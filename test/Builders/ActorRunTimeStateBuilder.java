/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Builders;

import HPalang.Core.SoftwareActor;
import HPalang.Core.Message;
import HPalang.LTSGeneration.RunTimeStates.SoftwareActorState;
import HPalang.LTSGeneration.RunTimeStates.ContinuousBehavior;
import HPalang.Core.Statement;
import HPalang.LTSGeneration.RunTimeStates.ContinuousBehaviorContianer;
import HPalang.LTSGeneration.RunTimeStates.ExecutionQueueState;
import HPalang.LTSGeneration.RunTimeStates.MessageQueueState;
import HPalang.LTSGeneration.RunTimeStates.ValuationState;
import HPalang.Utilities.Queue;


/**
 *
 * @author Iman Jahandideh
 */
public class ActorRunTimeStateBuilder
{
    private SoftwareActor actor;
    
    private boolean isSuspended = false;
    

    private final Queue<Message> lowPriorityMessages = new Queue<>();
    
    private final Queue<Statement> statements = new Queue<>();

    public ActorRunTimeStateBuilder WithActor(SoftwareActor actor)
    {
        this.actor = actor;
        return this;
    }
    
    public ActorRunTimeStateBuilder EnqueueLowPriorityMessage(Message message)
    {
        lowPriorityMessages.Enqueue(message);
        return this;
    }
       
    public ActorRunTimeStateBuilder SetSuspended(boolean suspended)
    {
        isSuspended = suspended;
        return this;
    }
        
    public ActorRunTimeStateBuilder EnqueueStatement(Statement statement)
    {
        statements.Enqueue(statement);
        return this;
    }

    public SoftwareActorState Build()
    {
        SoftwareActorState actorState = new SoftwareActorState(actor);
        MessageQueueState messageQueueState = new MessageQueueState();
        ExecutionQueueState executionQueueState = new ExecutionQueueState();
        ValuationState valuationState = new ValuationState(actor.GetDiscreteVariables());
        
        actorState.AddSubstate(messageQueueState);
        actorState.AddSubstate(executionQueueState);
        actorState.AddSubstate(valuationState);
        
        
        messageQueueState.Messages().Enqueue(lowPriorityMessages);
        executionQueueState.Statements().Enqueue(statements);
        
        actorState.SetSuspended(isSuspended);
        
        return actorState;
    }
}

