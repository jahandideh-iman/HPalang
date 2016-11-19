/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.SOSRules;

import Builders.ActorBuilder;
import Builders.ActorRunTimeStateBuilder;
import Builders.GlobalRunTimeStateBuilder;
import HPalang.Core.Actor;
import HPalang.Core.MessageHandler;
import HPalang.LTSGeneration.LTSGenerator;
import HPalang.LTSGeneration.LabeledTransitionSystem;
import HPalang.Core.Message;
import HPalang.Core.Messages.NormalMessage;
import HPalang.LTSGeneration.RunTimeStates.GlobalRunTimeState;
import HPalang.LTSGeneration.TauLabel;
import HPalang.Core.Statements.SendStatement;
import HPalang.LTSGeneration.Transition;
import Mocks.EmptyMessage;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.equalTo;
import org.junit.Before;

/**
 *
 * @author Iman Jahandideh
 */
public class MessageSendRuleTest
{
    LTSGenerator ltsGenerator = new LTSGenerator();
    LabeledTransitionSystem generatedLTS;
    GlobalRunTimeStateBuilder globalState = new GlobalRunTimeStateBuilder();
    
    @Before
    public void Setup()
    {
        ltsGenerator.AddSOSRule(new MessageSendRule());
    }
    
    @Test
    public void ForEachActorStateIfThereIsSendStatementSendsTheMessage()
    {
        Actor actor1 = new ActorBuilder().WithID("actor1").WithCapacity(1).WithHandler("a", new MessageHandler()).Build();
        Actor actor2 = new ActorBuilder().WithID("actor2").WithCapacity(1).WithHandler("b", new MessageHandler()).Build();
        
        Message messageTo2 = new NormalMessage(actor2.GetMessageHandler("b"));
        Message messageTo1 = new NormalMessage(actor1.GetMessageHandler("a"));

        ActorRunTimeStateBuilder actor1State = new ActorRunTimeStateBuilder()
                .WithActor(actor1)
                .EnqueueStatement(new SendStatement(actor2, messageTo2));
        
        ActorRunTimeStateBuilder actor2State = new ActorRunTimeStateBuilder()
                .WithActor(actor2)
                .EnqueueStatement(new SendStatement(actor1, messageTo1));
        
        globalState
                .AddActorRunTimeState(actor1State)
                .AddActorRunTimeState(actor2State);
                
                  
        generatedLTS = ltsGenerator.Generate(globalState.Build());
        
        GlobalRunTimeState stateAfterMessageTo2Sent = globalState.Build();
        stateAfterMessageTo2Sent.FindActorState(actor1).DequeueNextStatement();
        stateAfterMessageTo2Sent.FindActorState(actor2).EnqueueMessage(messageTo2);
        
        GlobalRunTimeState sateAfterMessageTo1Sent = globalState.Build();
        sateAfterMessageTo1Sent.FindActorState(actor2).DequeueNextStatement();
        sateAfterMessageTo1Sent.FindActorState(actor1).EnqueueMessage(messageTo1);
        
        assertTrue(generatedLTS.HasTransition(globalState.Build(), new TauLabel(), stateAfterMessageTo2Sent));
        assertTrue(generatedLTS.HasTransition(globalState.Build(), new TauLabel(), stateAfterMessageTo2Sent));
    } 
    
    @Test
    public void DropsTheMessageIfTheRecieverMessageQueueIsFull()
    {
        Actor actor = new ActorBuilder().WithID("actor").WithCapacity(1).Build();
        
         ActorRunTimeStateBuilder fullCapacityActorState = new ActorRunTimeStateBuilder()
                .WithActor(actor)
                .EnqueueMessage(new EmptyMessage("Message1"))
                .EnqueueStatement(new SendStatement(actor, new EmptyMessage("Message2")));
        
        globalState
                .AddActorRunTimeState(fullCapacityActorState);
                
                  
        generatedLTS = ltsGenerator.Generate(globalState.Build());
       
        GlobalRunTimeState droppedState = globalState.Build();
        droppedState.FindActorState(actor).DequeueNextStatement();
        
        List<Transition> transitions =  generatedLTS.GetTransitionsFrom(globalState.Build());
        
        assertThat(transitions.size(),equalTo(1) );
        assertThat(transitions.get(0).GetDestination(), equalTo(droppedState));
    }
}
