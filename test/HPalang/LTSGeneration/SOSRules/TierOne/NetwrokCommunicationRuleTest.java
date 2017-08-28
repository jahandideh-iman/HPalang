/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.SOSRules.TierOne;

import Builders.SoftwareActorBuilder;
import Builders.SoftwareActorStateBuilder;
import HPalang.Core.SoftwareActor;
import HPalang.Core.Message;
import HPalang.Core.MessageArguments;
import HPalang.Core.MessagePacket;
import HPalang.LTSGeneration.LTSGenerator;
import HPalang.LTSGeneration.Labels.NetworkLabel;
import HPalang.LTSGeneration.RunTimeStates.SoftwareActorState;
import HPalang.LTSGeneration.RunTimeStates.GlobalRunTimeState;
import HPalang.LTSGeneration.RunTimeStates.MessageQueueState;
import HPalang.LTSGeneration.RunTimeStates.NetworkState;
import HPalang.LTSGeneration.SOSRules.SOSRuleTestFixture;
import HPalang.LTSGeneration.Labels.SoftwareLabel;
import TestUtilities.Utilities;
import Mocks.EmptyMessage;
import static org.hamcrest.CoreMatchers.is;
import org.hamcrest.core.IsEqual;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
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
        ltsGenerator.AddSOSRule(new NetwrokCommunicationRule());
    }
    
   @Test
   public void SendsHighestPriorityMessageWhenIsNotIdleAndThereIsNoSoftwareTransition()
   {
       NetworkState networkState = new NetworkState();
       SoftwareActor receiver = Utilities.CreateSofwareActor("receiver");
       SoftwareActor sender = Utilities.CreateSofwareActor("sender");
       MessageArguments emptyArguments = new MessageArguments();
       
       SoftwareActorStateBuilder receiverState = new SoftwareActorStateBuilder()
               .WithActor(receiver);
       
       globalState.DiscreteState().AddSoftwareActorState(receiverState.Build());
       globalState.AddSubstate(networkState);
       
       
       Message m1 = new EmptyMessage();
       Message m2 = new EmptyMessage();
       m1.SetPriority(1);
       m2.SetPriority(2);
       
       MessagePacket lowPrioirityPacket = new MessagePacket(sender, receiver, m1, emptyArguments);
       MessagePacket highPrioirityPacket = new MessagePacket(sender, receiver, m2, emptyArguments);
       
       networkState.Buffer(lowPrioirityPacket);
       networkState.Buffer(highPrioirityPacket);
       
       generatedLTS = ltsGenerator.Generate(globalState);
        
       GlobalRunTimeState nextGlobalState = globalState.DeepCopy();
       SoftwareActorState nextActorState = nextGlobalState.DiscreteState().FindActorState(receiver);
       nextActorState.MessageQueueState().Messages().Enqueue(highPrioirityPacket);
       nextGlobalState.NetworkState().Debuffer(highPrioirityPacket);

       
       assertTrue(generatedLTS.HasTransition(globalState, new NetworkLabel(), nextGlobalState));       
   }
}
