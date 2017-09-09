/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.RunTimeStates.Event;

import HPalang.Core.Equalitable;
import HPalang.Core.MessagePacket;
import HPalang.Core.SoftwareActor;
import HPalang.LTSGeneration.RunTimeStates.ActorState;
import HPalang.LTSGeneration.RunTimeStates.GlobalRunTimeState;
import HPalang.LTSGeneration.RunTimeStates.SoftwareActorState;

/**
 *
 * @author Iman Jahandideh
 */
public class SendPacketAndResetNetworkAction extends Equalitable<SendPacketAndResetNetworkAction> implements Action 
{
    private final MessagePacket packet;
    
    public SendPacketAndResetNetworkAction(MessagePacket packet)
    {
        this.packet = packet;
    }
     
   @Override
    public void Execute(GlobalRunTimeState globalState)
    {
        globalState.NetworkState().SetIdle(true);
        
        ActorState receiverState = globalState.FindActorState(packet.Receiver());
        
        receiverState.MessageQueueState().Messages().Enqueue(packet);
    }
    
    @Override
    protected boolean InternalEquals(SendPacketAndResetNetworkAction other)
    {
        return this.packet.equals(other.packet);
    }

    @Override
    protected int InternalHashCode()
    {
        return 0;
    }
    
}
