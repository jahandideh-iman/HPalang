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
import HPalang.Core.ValuationContainer;
import HPalang.Core.Variable;
import HPalang.Core.VariableArgument;
import HPalang.Core.VariableParameter;
import HPalang.LTSGeneration.Labels.SoftwareLabel;
import HPalang.LTSGeneration.RunTimeStates.GlobalRunTimeState;
import HPalang.LTSGeneration.RunTimeStates.SoftwareActorState;
import HPalang.LTSGeneration.TransitionCollector;
import HPalang.Utilities.Queue;
import java.util.List;

/**
 *
 * @author Iman Jahandideh
 */
public class PriorityBasedMessageTakeRule extends SoftwareActorLevelRule
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
        
        MessagePacket packet = FindAndRemoveHighestPriorityMessage(newActorState.MessageQueueState().Messages());
        Message message = packet.Message();
        
               List<VariableParameter> parametersList =  message.Parameters().AsList();  
        List<VariableArgument> argumentsList = packet.Arguments().AsList();
        ValuationContainer valuation = newActorState.ValuationState().Valuation();
        
        for(int i = 0 ; i < parametersList.size(); i++)
        {
            Variable variable = parametersList.get(i).Variable();
            valuation.Add(variable);
            newActorState.ExecutionQueueState().Statements().Enqueue(
                    new AssignmentStatement(
                            variable, 
                            argumentsList.get(i).Value()));
            
        }
        
        newActorState.ExecutionQueueState().Statements().Enqueue(message.GetMessageBody());
        //newActorState.ExecutionQueueState().Statements().Enqueue(new MessageTeardownStatement(message.Parameters()));
        
        collector.AddTransition(new SoftwareLabel(), newGlobalState);
    }
    
    private MessagePacket FindAndRemoveHighestPriorityMessage(Queue<MessagePacket> packets)
    {
        MessagePacket highetPriortyPacket = packets.Head();
        
        for(MessagePacket packet : packets)
            if(packet.Priority() < highetPriortyPacket.Priority())
                highetPriortyPacket = packet;
        
        packets.Remove(highetPriortyPacket);
        return highetPriortyPacket;
    }
    
}
