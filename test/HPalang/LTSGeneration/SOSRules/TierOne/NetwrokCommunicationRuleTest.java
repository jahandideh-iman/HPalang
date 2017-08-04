/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.SOSRules.TierOne;

import Builders.ActorBuilder;
import Builders.ActorRunTimeStateBuilder;
import HPalang.Core.Actor;
import HPalang.Core.Message;
import HPalang.Core.NetworkPacket;
import HPalang.LTSGeneration.LTSGenerator;
import HPalang.LTSGeneration.Labels.NetworkLabel;
import HPalang.LTSGeneration.RunTimeStates.ActorRunTimeState;
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
       Actor receiver = Utilities.NewActor("receiver");
       Actor sender = Utilities.NewActor("sender");
       
       ActorRunTimeStateBuilder receiverState = new ActorRunTimeStateBuilder()
               .WithActor(receiver);
       
       globalState.AddActorRunTimeState(receiverState.Build());
       globalState.AddSubstate(networkState);
       
       
       Message m1 = new EmptyMessage();
       Message m2 = new EmptyMessage();
       m1.SetPriority(1);
       m2.SetPriority(2);
       
       networkState.Buffer(new NetworkPacket(sender, m1, receiver));
       networkState.Buffer(new NetworkPacket(sender, m2, receiver));
       
       generatedLTS = ltsGenerator.Generate(globalState);
        
       GlobalRunTimeState nextGlobalState = globalState.DeepCopy();
       ActorRunTimeState nextActorState = nextGlobalState.FindActorState(receiver);
       nextActorState.FindSubState(MessageQueueState.class).Messages().Enqueue(m2);
       NetworkState nextNetworkState = nextGlobalState.FindSubState(NetworkState.class);
       nextNetworkState.Debuffer(new NetworkPacket(sender, m2, receiver));
       
       assertTrue(generatedLTS.HasTransition(globalState, new NetworkLabel(), nextGlobalState));       
   }
}
