/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.SOSRules;

import HPalang.Core.MessagePacket;
import HPalang.LTSGeneration.Labels.NetworkLabel;
import HPalang.LTSGeneration.RunTimeStates.Event.SendPacketAndResetNetworkAction;
import HPalang.LTSGeneration.RunTimeStates.SoftwareActorState;
import HPalang.LTSGeneration.RunTimeStates.GlobalRunTimeState;
import HPalang.LTSGeneration.RunTimeStates.MessageQueueState;
import HPalang.LTSGeneration.RunTimeStates.NetworkState;
import HPalang.LTSGeneration.SOSRule;
import static HPalang.LTSGeneration.SOSRules.Utilities.HasSoftwareActions;
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
        
        if( !networkState.IsIdle() ||networkState.Buffer().isEmpty() || HasSoftwareActions(stateInfo.Outs()))
            return;
        
        MessagePacket packet = FindHighestPriority(networkState.Buffer());
        float networkDelay = packet.Sender().NetworkDelayFor(packet.Receiver(), packet.Message());
        networkState.SetIdle(false);
        newGlobalState.EventsState().RegisterEvent(networkDelay, new SendPacketAndResetNetworkAction(packet));
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
