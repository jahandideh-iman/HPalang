/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.SOSRules;

import HPalang.Core.Message;
import HPalang.Core.MessagePacket;
import HPalang.Core.Statements.AssignmentStatement;
import HPalang.Core.Statements.MessageTeardownStatement;
import HPalang.Core.VariableArgument;
import HPalang.Core.VariableParameter;
import HPalang.LTSGeneration.Labels.SoftwareLabel;
import HPalang.LTSGeneration.RunTimeStates.GlobalRunTimeState;
import HPalang.LTSGeneration.RunTimeStates.SoftwareActorState;
import HPalang.LTSGeneration.TransitionCollector;
import HPalang.Utilities.Queue;

/**
 *
 * @author Iman Jahandideh
 */
public class PriorityBasedMessageTakeRule extends ActorLevelRule
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
        SoftwareActorState newActorState = newGlobalState.DiscreteState().FindActorState(actorState.Actor());
        
        Message message = FindAndRemoveHighestPriorityMessage(newActorState.MessageQueueState().Messages());
        for(VariableArgument argument : message.Arguments().AsSet())
            newActorState.ExecutionQueueState().Statements().Enqueue(
                    new AssignmentStatement(
                            argument.Parameter().Variable(), 
                            argument.Value()));
        
        for(VariableParameter parameter : message.Parameters().AsSet())
            newActorState.ValuationState().Valuation().Add(parameter.Variable());
        
        newActorState.ExecutionQueueState().Statements().Enqueue(message.GetMessageBody());
        newActorState.ExecutionQueueState().Statements().Enqueue(new MessageTeardownStatement(message.Parameters()));
        
        collector.AddTransition(new SoftwareLabel(), newGlobalState);
    }
    
    private Message FindAndRemoveHighestPriorityMessage(Queue<MessagePacket> packets)
    {
        MessagePacket highetPriortyPacket = packets.Head();
        
        for(MessagePacket packet : packets)
            if(packet.Message().Priority() > highetPriortyPacket.Message().Priority())
                highetPriortyPacket = packet;
        
        packets.Remove(highetPriortyPacket);
        return highetPriortyPacket.Message();
    }
    
}
