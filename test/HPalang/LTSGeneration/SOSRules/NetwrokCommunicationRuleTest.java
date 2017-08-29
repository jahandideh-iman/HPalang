/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.SOSRules;

import Builders.SoftwareActorStateBuilder;
import HPalang.Core.SoftwareActor;
import HPalang.Core.Message;
import HPalang.Core.MessageArguments;
import HPalang.Core.MessagePacket;
import HPalang.LTSGeneration.Labels.NetworkLabel;
import HPalang.LTSGeneration.RunTimeStates.SoftwareActorState;
import HPalang.LTSGeneration.RunTimeStates.GlobalRunTimeState;
import HPalang.LTSGeneration.RunTimeStates.NetworkState;
import static TestUtilities.CoreUtility.*;
import Mocks.EmptyMessage;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Iman Jahandideh
 */
public class NetwrokCommunicationRuleTest extends SOSRuleTestFixture
{
    @Before
    public void Setup()
    {
        rule = new NetwrokCommunicationRule();
    }
    
   @Test
   public void SendsHighestPriorityMessageWhenIsNotIdleAndThereIsNoSoftwareTransition()
   {
       SoftwareActor receiver = CreateSofwareActor("receiver");
       SoftwareActor sender = CreateSofwareActor("sender");
       MessageArguments emptyArguments = new MessageArguments();
       
       SoftwareActorStateBuilder receiverState = new SoftwareActorStateBuilder()
               .WithActor(receiver);
       
       globalState.DiscreteState().AddSoftwareActorState(receiverState.Build());
       
       
       Message m1 = new EmptyMessage();
       Message m2 = new EmptyMessage();
       m1.SetPriority(1);
       m2.SetPriority(2);
       
       MessagePacket lowPrioirityPacket = new MessagePacket(sender, receiver, m1, emptyArguments);
       MessagePacket highPrioirityPacket = new MessagePacket(sender, receiver, m2, emptyArguments);
       
       globalState.NetworkState().Buffer(lowPrioirityPacket);
       globalState.NetworkState().Buffer(highPrioirityPacket);
       
       rule.TryApply(SimpleStateInfo(globalState), transitionCollectorChecker);
        
       GlobalRunTimeState nextGlobalState = globalState.DeepCopy();
       SoftwareActorState nextActorState = nextGlobalState.DiscreteState().FindActorState(receiver);
       nextActorState.MessageQueueState().Messages().Enqueue(highPrioirityPacket);
       nextGlobalState.NetworkState().Debuffer(highPrioirityPacket);
       
       transitionCollectorChecker.ExpectTransition(new NetworkLabel(), nextGlobalState);     
   }
}
