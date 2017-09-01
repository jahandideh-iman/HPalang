/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.SOSRules;

import HPalang.Core.CommunicationType;
import HPalang.Core.DiscreteExpressions.ConstantDiscreteExpression;
import HPalang.Core.DiscreteExpressions.VariableExpression;
import HPalang.Core.Expression;
import HPalang.Core.Message;
import HPalang.Core.MessageArguments;
import HPalang.Core.MessagePacket;
import HPalang.Core.RealVariablePool;
import HPalang.LTSGeneration.RunTimeStates.SoftwareActorState;
import HPalang.LTSGeneration.RunTimeStates.GlobalRunTimeState;
import HPalang.LTSGeneration.Labels.SoftwareLabel;
import HPalang.Core.Statements.SendStatement;
import HPalang.Core.SimpleValuationContainer;
import HPalang.Core.ValuationContainer;
import HPalang.Core.VariableArgument;
import HPalang.Core.VariableParameter;
import HPalang.Core.Variables.RealVariable;
import HPalang.LTSGeneration.Labels.Reset;
import HPalang.LTSGeneration.RunTimeStates.MessageQueueState;
import HPalang.LTSGeneration.TransitionCollector;
import java.util.HashSet;
import java.util.Set;
import org.antlr.v4.runtime.misc.Pair;

/**
 *
 * @author Iman Jahandideh
 */
public class MessageSendRule extends SoftwareActorLevelRule
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

        SoftwareActorState senderState = newGlobalState.DiscreteState().FindActorState(actorState.SActor());
        SendStatement sendStatement = (SendStatement)senderState.ExecutionQueueState().Statements().Dequeue();
        
        SoftwareActorState receiverState = newGlobalState.DiscreteState().FindActorState(sendStatement.Receiver());
        MessageQueueState reciverMessageQueueState = receiverState.MessageQueueState();
        
        ValuationContainer valuations = senderState.ValuationState().Valuation();
        RealVariablePool pool = globalState.VariablePoolState().Pool();
        
        Pair<MessageArguments, Set<Reset>> maximalEvaluatoionReslut = 
                CreateMaximalEvaluatedArguments(
                        sendStatement.Message(), 
                        sendStatement.Arguments(),
                        valuations,
                        pool
                );
        
        
        MessagePacket packet = new MessagePacket(
                senderState.SActor(), 
                receiverState.SActor(), 
                sendStatement.Message(), 
                maximalEvaluatoionReslut.a
        );
        
        CommunicationType communicationType = senderState.SActor().CommunicationTypeFor(receiverState.SActor());
        
        switch (communicationType) {
            case CAN:
                newGlobalState.NetworkState().Buffer(packet);
                break;
            case Wired:
                reciverMessageQueueState.Messages().Enqueue(packet);
                break;
            default:
                throw new RuntimeException("Invalid Communication Type.");
        }
        
        collector.AddTransition(new SoftwareLabel(maximalEvaluatoionReslut.b), newGlobalState);
    }
    
    // TODO: Refactor this crap.
    private Pair<MessageArguments, Set<Reset>>  CreateMaximalEvaluatedArguments(Message message, MessageArguments unEvaluatedArguments, ValuationContainer valuations, RealVariablePool pool)
    {
        MessageArguments maximalEvaluatedarguments = new MessageArguments();
        Set<Reset> resets = new HashSet<>();
        
        for(VariableParameter param : message.Parameters().AsSet())
        {
            Expression unEvalutedValue = unEvaluatedArguments.ArgumentFor(param).Value();
            VariableArgument argument;
            if(unEvalutedValue.IsComputable(valuations))
            {
                argument = new VariableArgument(
                        param, 
                        new ConstantDiscreteExpression(unEvalutedValue.Evaluate(valuations)));
            }
            
            else
            {
                RealVariable pooledVariable = pool.Acquire();
                
                argument = new VariableArgument(
                        param, 
                        new VariableExpression(pooledVariable));
                
                resets.add(new Reset(
                        pool.Acquire(), 
                        unEvalutedValue.PartiallyEvaluate(valuations)));
            }
            
            maximalEvaluatedarguments.Add(argument);
        }
        
        return new Pair(maximalEvaluatedarguments,resets);
    }

}
