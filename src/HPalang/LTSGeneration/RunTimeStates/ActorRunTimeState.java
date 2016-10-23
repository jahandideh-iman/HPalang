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
import java.util.Queue;

/**
 *
 * @author Iman Jahandideh
 */
public class ActorRunTimeState 
{
    private Actor actor;
    
    private boolean isDelayed = false;
    private DiscreteState discreteState = new DiscreteState();
    private ContinuousState continuousState = new ContinuousState();
    
    
    public ActorRunTimeState(Actor actor)
    {
        this.actor = actor;
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
        isDelayed = delayed;
    }
    
    public boolean IsDelayed()
    {
        return isDelayed;
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
    
    ActorRunTimeState Clone()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
