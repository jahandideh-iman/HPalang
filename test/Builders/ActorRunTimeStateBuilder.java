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
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 *
 * @author Iman Jahandideh
 */
public class ActorRunTimeStateBuilder
{
    private Actor actor;
    
    private boolean isDelayed = false;

    private Queue<Message> messages = new LinkedList<>();
    private Queue<Statement> statements = new LinkedList<>();
    
    private List<ContinuousBehavior> behaviors = new ArrayList<>();

    public ActorRunTimeStateBuilder WithActor(Actor actor)
    {
        this.actor = actor;
        return this;
    }
    
    public ActorRunTimeStateBuilder AddBehavior(ContinuousBehavior behavior)
    {
        behaviors.add(behavior);
        return this;
    }

    public ActorRunTimeStateBuilder EnqueueMessage(Message message)
    {
        messages.add(message);
        return this;
    }
    
    public ActorRunTimeStateBuilder SetDelayed(boolean delayed)
    {
        isDelayed = delayed;
        return this;
    }
    
    public ActorRunTimeStateBuilder EnqueueStatement(Statement statement)
    {
        statements.add(statement);
        return this;
    }

    public ActorRunTimeState Build()
    {
        ActorRunTimeState actorState = new ActorRunTimeState(actor);

        for(Message m : messages)
            actorState.EnqueueMessage(m);

        actorState.EnqueueStatements(statements);
        
        actorState.SetDelayed(isDelayed);
        
        for(ContinuousBehavior b : behaviors)
            actorState.AddContinuousBehavior(b);
        
        return actorState;
    }
}

