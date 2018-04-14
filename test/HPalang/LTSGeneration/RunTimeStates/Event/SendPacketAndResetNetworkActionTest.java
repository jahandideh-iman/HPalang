/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.RunTimeStates.Event;

import HPalang.Core.MessagePacket;
import HPalang.Core.SoftwareActor;
import HPalang.LTSGeneration.RunTimeStates.ActorState;
import HPalang.LTSGeneration.RunTimeStates.GlobalRunTimeState;
import HPalang.LTSGeneration.RunTimeStates.SoftwareActorState;
import static TestUtilities.CoreUtility.*;
import static TestUtilities.NetworkingUtility.*;
import static org.hamcrest.CoreMatchers.*;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 *
 * @author Iman Jahandideh
 */
public class SendPacketAndResetNetworkActionTest
{
    
    GlobalRunTimeState globalState = CreateGlobalState();
    SoftwareActorState receiverState = CreateSoftwareActorState("Receiver", 1);
    
    SendPacketAndResetNetworkAction action;
    MessagePacket packet;

    @Before
    public void Setup()
    {
        packet = EmptySelfMessagePacketFor(receiverState.SActor(),"Packet");
        
        globalState.DiscreteState().AddSoftwareActorState(receiverState);
        
        action = new SendPacketAndResetNetworkAction(packet);
    }
    
    @Test 
    public void MakesNetworkStateIdleWhenExecuted()
    {
        globalState.NetworkState().SetIdle(false);
        
        action.Execute(globalState);
        
        assertThat(globalState.NetworkState().IsIdle(), is(true));
    }
    
    @Test 
    public void SendsThePacketToDetinationWhenExecuted()
    {
        action.Execute(globalState);
        
        SoftwareActorState expectedState = FindActorState(receiverState.SActor(), globalState);
        
        assertThat(expectedState.MessageQueueState().Messages(), hasItem(packet));
    }
    
    @Test 
    public void IsNotDeadlockWhenMessageQueueIsNotFull()
    {      
        AssertActorQueueIsNotFull(receiverState);
        
        assertThat(action.IsDeadlock(globalState), is(false));
    }
    
    @Test 
    public void IsDeadlockWhenMessageQueueIsFull()
    {      
        FillUpActorsMessageQueue(receiverState);
        
        assertThat(action.IsDeadlock(globalState), is(true));
    }
    
    public void FillUpActorsMessageQueue(ActorState actorState)
    {
        
        while(actorState.MessageQueueState().Messages().Size() < actorState.Actor().QueueCapacity())
            actorState.MessageQueueState().Messages().Enqueue(EmptySelfMessagePacketFor(actorState.Actor(), "Temp"));
    }

    private void AssertActorQueueIsNotFull(SoftwareActorState actorState)
    {
        assertTrue(actorState.MessageQueueState().Messages().Size() < actorState.Actor().QueueCapacity());
    }
}
