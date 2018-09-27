/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.SOSRules;

import HPalang.Core.Actor;
import HPalang.Core.CANSpecification;
import HPalang.Core.CommunicationType;
import HPalang.Core.DiscreteExpressions.ConstantDiscreteExpression;
import HPalang.Core.DiscreteExpressions.VariableExpression;
import HPalang.Core.EmptyActor;
import HPalang.Core.Expression;
import HPalang.Core.Message;
import HPalang.Core.MessageArguments;
import HPalang.Core.MessagePacket;
import HPalang.Core.RealVariablePool;
import HPalang.Core.SingleCommunicationRealVariablePool;
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
import static HPalang.LTSGeneration.SOSRules.Utilities.UnWrapResetScope;
import HPalang.LTSGeneration.TransitionCollector;
import HPalang.LTSGeneration.Utilities.CreationUtility;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

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
        
        if(receiver instanceof EmptyActor)
            return true;
        
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
        Actor receiver = sendStatement.ReceiverLocator().Locate(actorState);
         
         if(receiver instanceof EmptyActor)
         {
             collector.AddTransition(new SoftwareLabel(), newGlobalState);
             return;
         }

        if(PoolHasEnoughAvaialbeVariables(globalState, actorState , sendStatement) == false)
        {
            collector.AddTransition(
                    CreationUtility.CreateDeadlockTransition(),
                    CreationUtility.CreateDeadlockState(DeadlockMessage(senderState, sendStatement)));
            return;
        }
        
       
        ActorState receiverState = newGlobalState.FindActorState(receiver);
        MessageQueueState reciverMessageQueueState = receiverState.MessageQueueState();
        
        ValuationContainer valuations = senderState.ValuationState().Valuation();
        RealVariablePool pool = newGlobalState.VariablePoolState().Pool();
        
        MaximalEvaluatedArgumentsResult maximalEvaluatoionResult = 
                CreateMaximalEvaluatedArguments(
                        senderState.Actor(),
                        receiver,
                        sendStatement.MessageLocator().Locate(actorState.Actor()), 
                        sendStatement.Arguments(),
                        valuations,
                        pool
                );
        
//        if(maximalEvaluatoionResult.resets.size()>0)
//            System.out.println(maximalEvaluatoionResult.resets);
        
        Message message = sendStatement.MessageLocator().Locate(actorState.Actor());
        int prioriy = MessagePriorityFor(receiverState.Actor(),message, globalState.NetworkState().CANSpecification());
        
        MessagePacket packet = new MessagePacket(
                senderState.Actor(), 
                receiverState.Actor(), 
                message, 
                maximalEvaluatoionResult.messageArguments,
                prioriy,
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
    private MaximalEvaluatedArgumentsResult  CreateMaximalEvaluatedArguments(Actor sender, Actor receiver, Message message, MessageArguments unEvaluatedArguments, ValuationContainer valuations, RealVariablePool pool)
    {   
        MessageArguments maximalEvaluatedarguments = new MessageArguments();
        Set<Reset> resets = new HashSet<>();
        List<RealVariable> pooledVariables = new LinkedList<>();
        
        List<VariableParameter> parametersList = message.Parameters().AsList();
        List<VariableArgument> unEvaluatedArgumentsList  = unEvaluatedArguments.AsList();
        
        //--------------------------- THIS IS AN AD HOCK (WRONG) SOLUTION -------------------------
        if(pool instanceof SingleCommunicationRealVariablePool 
                && parametersList.size()>0 )
        {
            List<RealVariable> variables = ((SingleCommunicationRealVariablePool) pool).Acquire(receiver, message);
            for(int i = 0 ; i< parametersList.size(); i++ )
            {
                RealVariable pooledVariable = variables.get(i);
                Expression unEvalutedValue = unEvaluatedArgumentsList.get(i).Value();
                pooledVariables.add(pooledVariable);
                VariableArgument argument = new VariableArgument(
                        parametersList.get(i).Type(), 
                        new VariableExpression(pooledVariable));
                
                resets.add(UnWrapResetScope(
                        new Reset(
                            pooledVariable, 
                            unEvalutedValue.PartiallyEvaluate(valuations)),
                        sender,
                        valuations
                        ));
                
                maximalEvaluatedarguments.Add(argument);
            }
            
            if(pooledVariables.size() != variables.size())
                System.err.println("s");
            return new MaximalEvaluatedArgumentsResult(maximalEvaluatedarguments, resets, pooledVariables);
        }

        //---------------------------------------------------------------------------------
        
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
                
                Reset rawReset = new Reset(
                        pooledVariable, 
                        unEvalutedValue.PartiallyEvaluate(valuations));
                
                
                resets.add(UnWrapResetScope(rawReset,sender, valuations));
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
        //--------------------------- THIS IS AN AD HOCK (WRONG) SOLUTION -------------------------
        if(globalState.VariablePoolState().Pool() instanceof SingleCommunicationRealVariablePool)
        {
                if(sendStatement.Arguments().AsList().size()>0)
                    return ((SingleCommunicationRealVariablePool)globalState.VariablePoolState().Pool())
                    .HasAvailableVariable(
                            sendStatement.ReceiverLocator().Locate(actorState),
                            sendStatement.MessageLocator().Locate(actorState.Actor())
                            );
                else
                    return true;
        }
        //---------------------------------------------------------------------------------
        return globalState.VariablePoolState().Pool().HasAvailableVariable(numberOfNeededVariables);
        
        
                
    }

    
    private int MessagePriorityFor(Actor actor, Message message, CANSpecification canSpecification)
    {
        if(canSpecification.HasNetworkPriorityFor(actor, message))
            return canSpecification.NetworkPriorityFor(actor, message);
        return 0;
    }

    private String DeadlockMessage(ActorState senderState, SendStatement statement)
    {
        Actor reciever = statement.ReceiverLocator().Locate(senderState);
        Message message = statement.MessageLocator().Locate(senderState.Actor());
        
        //return String.format("PoolEmpty__%s__%s__%s", senderState.Actor().Name(), message.toString(), reciever.Name());
        return "";
    }
}
