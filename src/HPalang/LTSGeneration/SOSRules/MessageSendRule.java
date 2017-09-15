/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.SOSRules;

import HPalang.Core.Actor;
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
import HPalang.Core.ValuationContainers.SimpleValuationContainer;
import HPalang.Core.SoftwareActor;
import HPalang.Core.ValuationContainer;
import HPalang.Core.VariableArgument;
import HPalang.Core.VariableParameter;
import HPalang.Core.Variables.RealVariable;
import HPalang.LTSGeneration.Labels.Reset;
import HPalang.LTSGeneration.RunTimeStates.ActorState;
import HPalang.LTSGeneration.RunTimeStates.MessageQueueState;
import HPalang.LTSGeneration.TransitionCollector;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.antlr.v4.runtime.misc.Pair;

/**
 *
 * @author Iman Jahandideh
 */
public class MessageSendRule extends ActorLevelRule
{
    @Override
    protected boolean IsRuleSatisfied(ActorState actorState, GlobalRunTimeState globalState)
    {
        if((actorState.ExecutionQueueState().Statements().Head() instanceof SendStatement) == false)
            return false;
        
        SendStatement sendStatement = (SendStatement)actorState.ExecutionQueueState().Statements().Head();
        
        Actor receiver = sendStatement.ReceiverLocator().Locate(actorState);
        ActorState receiverState = globalState.FindActorState(receiver);
        
        MessageQueueState queueState = receiverState.MessageQueueState();
        return  queueState.Messages().Size() < receiver.QueueCapacity() &&
                PoolHasEnoughAvaialbeVariables(globalState, actorState , sendStatement);
    }

    @Override
    protected void ApplyToActorState(ActorState actorState, GlobalRunTimeState globalState, TransitionCollector collector)
    {
        GlobalRunTimeState newGlobalState = globalState.DeepCopy();

        ActorState senderState = newGlobalState.FindActorState(actorState.Actor());
        SendStatement sendStatement = (SendStatement)senderState.ExecutionQueueState().Statements().Dequeue();
        
        Actor receiver = sendStatement.ReceiverLocator().Locate(actorState);
        ActorState receiverState = newGlobalState.FindActorState(receiver);
        MessageQueueState reciverMessageQueueState = receiverState.MessageQueueState();
        
        ValuationContainer valuations = senderState.ValuationState().Valuation();
        RealVariablePool pool = newGlobalState.VariablePoolState().Pool();
        
        Pair<MessageArguments, Set<Reset>> maximalEvaluatoionReslut = 
                CreateMaximalEvaluatedArguments(
                        sendStatement.MessageLocator().Locate(actorState.Actor()), 
                        sendStatement.Arguments(),
                        valuations,
                        pool
                );
        
        
        MessagePacket packet = new MessagePacket(
                senderState.Actor(), 
                receiverState.Actor(), 
                sendStatement.MessageLocator().Locate(actorState.Actor()), 
                maximalEvaluatoionReslut.a
        );
        
        CommunicationType communicationType = senderState.Actor().CommunicationTypeFor(receiverState.Actor());
        
        switch (communicationType) {
            case CAN:
                newGlobalState.NetworkState().Buffer(packet);
                break;
            case Wire:
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
        
        List<VariableParameter> parametersList = message.Parameters().AsList();
        List<VariableArgument> unEvaluatedArgumentsList  = unEvaluatedArguments.AsList();
        for(int i = 0 ; i< parametersList.size(); i++ )
        {
            Expression unEvalutedValue = unEvaluatedArgumentsList.get(i).Value();
            VariableArgument argument;
            if(unEvalutedValue.IsComputable(valuations))
            {
                argument = new VariableArgument(
                        parametersList.get(i).Type(), 
                        new ConstantDiscreteExpression(unEvalutedValue.Evaluate(valuations)));
            }
            
            else
            {
                RealVariable pooledVariable = pool.Acquire();
                
                argument = new VariableArgument(
                        parametersList.get(i).Type(), 
                        new VariableExpression(pooledVariable));
                
                resets.add(new Reset(
                        pooledVariable, 
                        unEvalutedValue.PartiallyEvaluate(valuations)));
            }
            
            maximalEvaluatedarguments.Add(argument);
        }
        
        return new Pair(maximalEvaluatedarguments,resets);
    }

    private boolean PoolHasEnoughAvaialbeVariables(GlobalRunTimeState globalState, ActorState actorState, SendStatement sendStatement)
    {
        int numberOfNeededVariables = 0;
        
        ValuationContainer valuations = actorState.ValuationState().Valuation();
        
        for(VariableArgument argument : sendStatement.Arguments().AsList())
            if(argument.Value().IsComputable(valuations) == false)
                numberOfNeededVariables++;
        
        //System.out.println("Number of needed:" + numberOfNeededVariables);
        return globalState.VariablePoolState().Pool().HasAvailableVariable(numberOfNeededVariables);
                
    }

}
