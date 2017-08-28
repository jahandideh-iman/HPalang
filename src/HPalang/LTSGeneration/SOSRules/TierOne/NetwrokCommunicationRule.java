/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.SOSRules.TierOne;

import HPalang.Core.MessagePacket;
import HPalang.LTSGeneration.Labels.NetworkLabel;
import HPalang.LTSGeneration.RunTimeStates.SoftwareActorState;
import HPalang.LTSGeneration.RunTimeStates.GlobalRunTimeState;
import HPalang.LTSGeneration.RunTimeStates.MessageQueueState;
import HPalang.LTSGeneration.RunTimeStates.NetworkState;
import HPalang.LTSGeneration.SOSRule;
import HPalang.LTSGeneration.StateInfo;
import HPalang.LTSGeneration.TransitionCollector;
import java.util.Collection;

/**
 *
 * @author Iman Jahandideh
 */
public class NetwrokCommunicationRule implements SOSRule
{

    @Override
    public void TryApply(StateInfo stateInfo, TransitionCollector generator)
    {
        GlobalRunTimeState newGlobalState = stateInfo.State().DeepCopy();
        NetworkState networkState = newGlobalState.FindSubState(NetworkState.class);
        
        if(networkState.Buffer().isEmpty())
            return;
        
        MessagePacket packet = FindHighestPriority(networkState.Buffer());
        
        SoftwareActorState receiverState = newGlobalState.DiscreteState().FindActorState(packet.Receiver());
        MessageQueueState messageQueueState = receiverState.FindSubState(MessageQueueState.class);
        messageQueueState.Messages().Enqueue(packet);
        networkState.Debuffer(packet);
        
        generator.AddTransition(new NetworkLabel(), newGlobalState); 
    }

    private MessagePacket FindHighestPriority(Collection<MessagePacket> buffer)
    {
        MessagePacket maxPacket = buffer.iterator().next();
        for( MessagePacket packet : buffer)
        {
            if(packet.Message().Priority() > maxPacket.Message().Priority())
                maxPacket = packet;
        }      
        return maxPacket;
    }
    
}
