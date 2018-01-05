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
import HPalang.LTSGeneration.RunTimeStates.GlobalRunTimeState;
import HPalang.LTSGeneration.Labels.SoftwareLabel;
import HPalang.Core.Statements.SendStatement;
import HPalang.Core.ValuationContainer;
import HPalang.Core.VariableArgument;
import HPalang.Core.VariableParameter;
import HPalang.Core.Variables.RealVariable;
import HPalang.LTSGeneration.Labels.Reset;
import HPalang.LTSGeneration.RunTimeStates.ActorState;
import HPalang.LTSGeneration.RunTimeStates.MessageQueueState;
import HPalang.LTSGeneration.TransitionCollector;
import HPalang.LTSGeneration.Utilities.CreationUtility;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import org.antlr.v4.runtime.misc.Pair;

/**
 *
 * @author Iman Jahandideh
 */
public abstract class MessageSendRule extends ActorLevelRule
{

    private class MaximalEvaluatedArgumentsResult
    {
        public final MessageArguments messageArguments;
        public final Set<Reset> resets;
        public final List<RealVariable> pooledVariables;

        private MaximalEvaluatedArgumentsResult(MessageArguments maximalEvaluatedarguments, Set<Reset> resets, List<RealVariable> pooledVariables)
        {
            this.messageArguments = maximalEvaluatedarguments;
            this.resets = resets;
            this.pooledVariables = pooledVariables;
        }
    }
    
    @Override
    protected boolean IsRuleSatisfied(ActorState actorState, GlobalRunTimeState globalState)
    {
        if(actorState.IsSuspended() == true)
            return false;
        
        if((actorState.ExecutionQueueState().Statements().Head() instanceof SendStatement) == false)
            return false;
        
        SendStatement sendStatement = (SendStatement)actorState.ExecutionQueueState().Statements().Head();
        
        Actor receiver = sendStatement.ReceiverLocator().Locate(actorState);
        ActorState receiverState = globalState.FindActorState(receiver);
        Message message = sendStatement.MessageLocator().Locate(actorState.Actor());
        
        
        return  IsCommunicationTypeSatisfied( actorState.Actor().CommunicationTypeFor(receiverState.Actor())) &&
                InternalIsRuleSatisfied(globalState, sendStatement, message, receiverState);
    }

    private boolean IsCommunicationTypeSatisfied(CommunicationType communicationType)
    {
        return communicationType == RuleCommunicationType();
    }
   
    abstract protected CommunicationType RuleCommunicationType();
    abstract protected boolean InternalIsRuleSatisfied(GlobalRunTimeState globalState, SendStatement sendStatement, Message message, ActorState receiverState);
        
    // TODO: Refactor this crap.
    @Override
    protected void ApplyToActorState(ActorState actorState, GlobalRunTimeState globalState, TransitionCollector collector)
    {          
        GlobalRunTimeState newGlobalState = globalState.DeepCopy();


        ActorState senderState = newGlobalState.FindActorState(actorState.Actor());
        SendStatement sendStatement = (SendStatement)senderState.ExecutionQueueState().Statements().Dequeue();
        

        if(PoolHasEnoughAvaialbeVariables(globalState, actorState , sendStatement) == false)
        {
            collector.AddTransition(CreationUtility.CreateDeadlockTransition(), CreationUtility.CreateDeadlockState());
            return;
        }
        
        Actor receiver = sendStatement.ReceiverLocator().Locate(actorState);
        ActorState receiverState = newGlobalState.FindActorState(receiver);
        MessageQueueState reciverMessageQueueState = receiverState.MessageQueueState();
        
        ValuationContainer valuations = senderState.ValuationState().Valuation();
        RealVariablePool pool = newGlobalState.VariablePoolState().Pool();
        
        MaximalEvaluatedArgumentsResult maximalEvaluatoionResult = 
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
                maximalEvaluatoionResult.messageArguments,
                maximalEvaluatoionResult.pooledVariables
        );
        
        InternalApply(newGlobalState, reciverMessageQueueState, packet);

        if(InternalMustGoToDeadlock(globalState, packet))
            collector.AddTransition(CreationUtility.CreateDeadlockTransition(), CreationUtility.CreateDeadlockState());
        
        else
            collector.AddTransition(new SoftwareLabel(maximalEvaluatoionResult.resets), newGlobalState);
    }
    
    abstract protected boolean InternalMustGoToDeadlock(GlobalRunTimeState globalState, MessagePacket packet);

    abstract protected void InternalApply(GlobalRunTimeState newGlobalState, MessageQueueState newRecieverMessageQueueState, MessagePacket packet);
    
    // TODO: Refactor this crap.
    private MaximalEvaluatedArgumentsResult  CreateMaximalEvaluatedArguments(Message message, MessageArguments unEvaluatedArguments, ValuationContainer valuations, RealVariablePool pool)
    {   
        MessageArguments maximalEvaluatedarguments = new MessageArguments();
        Set<Reset> resets = new HashSet<>();
        List<RealVariable> pooledVariables = new LinkedList<>();
        
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
                
                pooledVariables.add(pooledVariable);
                argument = new VariableArgument(
                        parametersList.get(i).Type(), 
                        new VariableExpression(pooledVariable));
                
                resets.add(new Reset(
                        pooledVariable, 
                        unEvalutedValue.PartiallyEvaluate(valuations)));
            }
            
            maximalEvaluatedarguments.Add(argument);
        }
        
        return new MaximalEvaluatedArgumentsResult(maximalEvaluatedarguments, resets, pooledVariables);
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
