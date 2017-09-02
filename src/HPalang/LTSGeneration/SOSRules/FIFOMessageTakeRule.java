/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.SOSRules;

import HPalang.Core.Message;
import HPalang.Core.Message.MessageType;
import HPalang.Core.MessagePacket;
import HPalang.Core.Statements.AssignmentStatement;
import HPalang.Core.Statements.MessageTeardownStatement;
import HPalang.Core.VariableArgument;
import HPalang.Core.VariableParameter;
import HPalang.LTSGeneration.Labels.SoftwareLabel;
import HPalang.LTSGeneration.RunTimeStates.SoftwareActorState;
import HPalang.LTSGeneration.RunTimeStates.GlobalRunTimeState;
import HPalang.LTSGeneration.TransitionCollector;
import HPalang.Utilities.Queue;

/**
 *
 * @author Iman Jahandideh
 */
public class FIFOMessageTakeRule extends SoftwareActorLevelRule
{

    @Override
    protected boolean IsRuleSatisfied(SoftwareActorState actorState, GlobalRunTimeState globalState)
    {
        return  actorState.ExecutionQueueState().Statements().IsEmpty() &&
                !actorState.MessageQueueState().Messages().IsEmpty() &&
                !actorState.IsSuspended();
    }

    @Override
    protected void ApplyToActorState(SoftwareActorState actorState, GlobalRunTimeState globalState, TransitionCollector collector)
    {
        GlobalRunTimeState newGlobalState = globalState.DeepCopy();
        SoftwareActorState newActorState = newGlobalState.DiscreteState().FindActorState(actorState.SActor());
        
        MessagePacket packet = FindAndRemoveAppropriateMessagePacket(newActorState.MessageQueueState().Messages());
        
        Message message = packet.Message();
        
        for(VariableParameter parameter : message.Parameters().AsSet())
            newActorState.ValuationState().Valuation().Add(parameter.Variable());
        
        for(VariableArgument argument : packet.Arguments().AsSet())
            newActorState.ExecutionQueueState().Statements().Enqueue(
                    new AssignmentStatement(
                            argument.Parameter().Variable(), 
                            argument.Value()));
        
        newActorState.ExecutionQueueState().Statements().Enqueue(message.GetMessageBody());
        newActorState.ExecutionQueueState().Statements().Enqueue(new MessageTeardownStatement(message.Parameters()));
        
        collector.AddTransition(new SoftwareLabel(), newGlobalState);
    }
    
    private MessagePacket FindAndRemoveAppropriateMessagePacket(Queue<MessagePacket> packets)
    {
        MessagePacket packet = null;
        packet = FindFirstDataMessagePacket(packets);
        if(packet == null)
            packet = FindFirstMessage(packets);
        
        packets.Remove(packet);
        return packet;
    }
    
    private MessagePacket FindFirstDataMessagePacket(Queue<MessagePacket> packets)
    {
        for(MessagePacket packet : packets)
            if(packet.Message().MessageType() == MessageType.Data)
                return packet;
        
        return null;
    }

    private MessagePacket FindFirstMessage(Queue<MessagePacket> packets)
    {
        return packets.Head();
    }
    
}
