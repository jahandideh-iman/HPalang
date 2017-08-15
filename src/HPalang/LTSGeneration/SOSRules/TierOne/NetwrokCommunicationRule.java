/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.SOSRules.TierOne;

import HPalang.Core.SoftwareActor;
import HPalang.Core.Message;
import HPalang.Core.NetworkPacket;
import HPalang.LTSGeneration.LTSGenerator;
import HPalang.LTSGeneration.Labels.NetworkLabel;
import HPalang.LTSGeneration.RunTimeStates.ActorRunTimeState;
import HPalang.LTSGeneration.RunTimeStates.GlobalRunTimeState;
import HPalang.LTSGeneration.RunTimeStates.MessageQueueState;
import HPalang.LTSGeneration.RunTimeStates.NetworkState;
import HPalang.LTSGeneration.SOSRule;
import HPalang.LTSGeneration.StateInfo;
import java.util.Collection;

/**
 *
 * @author Iman Jahandideh
 */
public class NetwrokCommunicationRule implements SOSRule
{

    @Override
    public void TryApply(StateInfo stateInfo, LTSGenerator generator)
    {
        GlobalRunTimeState newGlobalState = stateInfo.State().DeepCopy();
        NetworkState networkState = newGlobalState.FindSubState(NetworkState.class);
        
        if(networkState.Buffer().isEmpty())
            return;
        
        NetworkPacket packet = FindHighestPriority(networkState.Buffer());
        
        ActorRunTimeState receiverState = newGlobalState.FindActorState(packet.Receiver());
        MessageQueueState messageQueueState = receiverState.FindSubState(MessageQueueState.class);
        messageQueueState.Messages().Enqueue(packet.Message());
        networkState.Debuffer(packet);
        
        generator.AddTransition(new NetworkLabel(), newGlobalState); 
    }

    private NetworkPacket FindHighestPriority(Collection<NetworkPacket> buffer)
    {
        NetworkPacket maxPacket = buffer.iterator().next();
        for( NetworkPacket packet : buffer)
        {
            if(packet.Message().Priority() > maxPacket.Message().Priority())
                maxPacket = packet;
        }      
        return maxPacket;
    }
    
}
