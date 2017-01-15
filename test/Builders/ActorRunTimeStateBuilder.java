/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Builders;

import HPalang.Core.Actor;
import HPalang.Core.Message;
import HPalang.LTSGeneration.RunTimeStates.ActorRunTimeState;
import HPalang.LTSGeneration.RunTimeStates.ContinuousBehavior;
import HPalang.Core.Statement;
import HPalang.LTSGeneration.RunTimeStates.ContinuousBehaviorContianer;
import HPalang.LTSGeneration.RunTimeStates.Queue;


/**
 *
 * @author Iman Jahandideh
 */
public class ActorRunTimeStateBuilder
{
    private Actor actor;
    
    private boolean isDelayed = false;

    private Queue<Message> highPriorityMessages = new Queue<>();
    private Queue<Message> lowPriorityMessages = new Queue<>();
    
    private Queue<Statement> statements = new Queue<>();
    
    private ContinuousBehaviorContianer behaviors = new ContinuousBehaviorContianer();

    public ActorRunTimeStateBuilder WithActor(Actor actor)
    {
        this.actor = actor;
        return this;
    }
    
    public ActorRunTimeStateBuilder AddBehavior(ContinuousBehavior behavior)
    {
        behaviors.Add(behavior);
        return this;
    }

    public ActorRunTimeStateBuilder EnqueueLowPriorityMessage(Message message)
    {
        lowPriorityMessages.Enqueue(message);
        return this;
    }
    
    public ActorRunTimeStateBuilder EnqueueHighPriorityMessage(Message message)
    {
        highPriorityMessages.Enqueue(message);
        return this;
    }
     
    public ActorRunTimeStateBuilder SetDelayed(boolean delayed)
    {
        isDelayed = delayed;
        return this;
    }
    
    public ActorRunTimeStateBuilder EnqueueStatement(Statement statement)
    {
        statements.Enqueue(statement);
        return this;
    }

    public ActorRunTimeState Build()
    {
        ActorRunTimeState actorState = new ActorRunTimeState(actor);

        actorState.LowPriorityMessageQueue().Enqueue(lowPriorityMessages);
        actorState.HighPriorityMessageQueue().Enqueue(highPriorityMessages);

        actorState.StatementQueue().Enqueue(statements);
        
        actorState.SetSuspended(isDelayed);
        
        for(ContinuousBehavior b : behaviors)
            actorState.ContinuousBehaviors().Add(b);
        
        return actorState;
    }
}

