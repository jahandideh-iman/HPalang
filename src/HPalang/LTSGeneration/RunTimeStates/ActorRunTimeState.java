/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.RunTimeStates;

import HPalang.Core.Actor;
import HPalang.LTSGeneration.ContinuousBehavior;
import HPalang.LTSGeneration.Message;
import HPalang.Statements.SendStatement;
import HPalang.Statements.Statement;
import java.sql.Struct;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Queue;

/**
 *
 * @author Iman Jahandideh
 */
public class ActorRunTimeState implements Cloneable
{
    private final Actor actor;
    
    private DiscreteState discreteState = new DiscreteState();
    private ContinuousState continuousState = new ContinuousState();
    
    
    public ActorRunTimeState(Actor actor)
    {
        this.actor = actor;
    }
    
    public DiscreteState GetDiscreteState()
    {
        return discreteState;
    }
    
    public ContinuousState GetContinuousState()
    {
        return continuousState;
    }
       
    public void EnqueueMessage(Message message)
    {
        discreteState.EnqueueMessage(message);
    }
    
    public Queue<Message> GetMessages()
    {
        return discreteState.GetMessages();
    }

    public void RemoveMessage(Message message)
    {
        discreteState.RemoveMessage(message);
    }
            
    public Message GetNextMessage()
    {
        return discreteState.GetNextMessage();
    }
    
    public void DequeueNextMessage()
    {
        discreteState.DequeueNextMessage();
    }
    
    public boolean HasMessage()
    {
        return discreteState.HasMessage();
    }
    
    public Actor GetActor()
    {
       return actor;
    }
    
    public Statement GetNextStatement()
    {
        return discreteState.GetNextStatement();
    }
    
    public void DequeueNextStatement()
    {
        discreteState.DequeueNextStatement();
    }
    
    public void EnqueueStatements(Queue<Statement> statements)
    {
        for(Statement statement : statements)
            discreteState.EnqueueStatement(statement);
    }
    
    public void SetDelayed(boolean delayed)
    {
        discreteState.SetDelayed(delayed);
    }
    
    public boolean IsDelayed()
    {
        return discreteState.IsDelayed();
    }
     
    public void AddContinuousBehavior(ContinuousBehavior behavior)
    {
        continuousState.AddBehavior(behavior);
    }
    
    public List<ContinuousBehavior> GetContinuousBehaviors()
    {
        return continuousState.GetBehaviors();
    }
    
    public void RemoveContinuousBehavior(ContinuousBehavior behavior)
    {
        continuousState.RemoveBehavior(behavior);
    }
    
    public boolean HasStatement()
    {
        return discreteState.HasStatement();
    }
    
    @Override
    public boolean equals(Object other)
    {
         if(other == null)
            return false;
        
        if (!ActorRunTimeState.class.isAssignableFrom(other.getClass()))
            return false;
            
        ActorRunTimeState otherState = (ActorRunTimeState) other;
       
        return this.actor == otherState.actor
                && this.discreteState.equals(otherState.discreteState)
                && this.continuousState.equals(otherState.continuousState);
    }

    @Override
    public int hashCode()
    {
        return 1; // TODO: Find a better hashChode.
    }
    
    ActorRunTimeState Clone()
    {
        try {
            ActorRunTimeState copy = (ActorRunTimeState) clone();
            copy.continuousState = this.continuousState.Clone();
            copy.discreteState = this.discreteState.Clone();
            return copy;
        } catch (CloneNotSupportedException ex) {
            return null;
        }
    }

}
