/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.SOSRules;

import HPalang.Core.DiscreteExpressions.ConstantDiscreteExpression;
import HPalang.Core.Expression;
import HPalang.Core.Message;
import HPalang.Core.MessageArguments;
import HPalang.Core.MessagePacket;
import HPalang.LTSGeneration.RunTimeStates.SoftwareActorState;
import HPalang.LTSGeneration.RunTimeStates.GlobalRunTimeState;
import HPalang.LTSGeneration.Labels.SoftwareLabel;
import HPalang.Core.Statements.SendStatement;
import HPalang.Core.ValuationContainer;
import HPalang.Core.VariableArgument;
import HPalang.Core.VariableParameter;
import HPalang.LTSGeneration.RunTimeStates.MessageQueueState;
import HPalang.LTSGeneration.TransitionCollector;

/**
 *
 * @author Iman Jahandideh
 */
public class MessageSendRule extends ActorLevelRule
{
    @Override
    protected boolean IsRuleSatisfied(SoftwareActorState actorState, GlobalRunTimeState globalState)
    {
        if((actorState.ExecutionQueueState().Statements().Head() instanceof SendStatement) == false)
            return false;
        
        SendStatement sendStatement = (SendStatement)actorState.ExecutionQueueState().Statements().Head();
        SoftwareActorState receiverState = globalState.DiscreteState().FindActorState(sendStatement.Receiver());
        
        MessageQueueState queueState = receiverState.MessageQueueState();
        return  queueState.Messages().Size() < receiverState.GetMessageQueueCapacity();
    }

    @Override
    protected void ApplyToActorState(SoftwareActorState actorState, GlobalRunTimeState globalState, TransitionCollector collector)
    {
        GlobalRunTimeState newGlobalState = globalState.DeepCopy();

        SoftwareActorState senderState = newGlobalState.DiscreteState().FindActorState(actorState.Actor());
        SendStatement sendStatement = (SendStatement)senderState.ExecutionQueueState().Statements().Dequeue();
        
        SoftwareActorState receiverState = newGlobalState.DiscreteState().FindActorState(sendStatement.Receiver());
        MessageQueueState reciverMessageQueueState = receiverState.MessageQueueState();
        
        ValuationContainer valuations = senderState.ValuationState().Valuation();
        
        MessageArguments arguments = CreateMaximalEvaluatedArguments(sendStatement.Message(), sendStatement.Arguments(),valuations);
        
        MessagePacket packet = new MessagePacket(
                senderState.Actor(), 
                receiverState.Actor(), 
                sendStatement.Message(), 
                arguments
        );
        
        reciverMessageQueueState.Messages().Enqueue(packet);
        
        collector.AddTransition(new SoftwareLabel(), newGlobalState);
    }
    
    private MessageArguments CreateMaximalEvaluatedArguments(Message message, MessageArguments unEvaluatedArguments, ValuationContainer valuations)
    {
        MessageArguments maximalEvaluatedarguments = new MessageArguments();
        
        for(VariableParameter param : message.Parameters().AsSet())
        {
            Expression unEvalutedValue = unEvaluatedArguments.ArgumentFor(param).Value();
            VariableArgument argument = new VariableArgument(
                    param, 
                    new ConstantDiscreteExpression(unEvalutedValue.Evaluate(valuations)));
            maximalEvaluatedarguments.Add(argument);
        }
        
        return maximalEvaluatedarguments;
    }
}
