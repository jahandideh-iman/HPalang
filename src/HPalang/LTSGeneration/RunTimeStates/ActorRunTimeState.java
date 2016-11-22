/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.RunTimeStates;

import HPalang.Core.Actor;
import HPalang.Core.Message;
import HPalang.Core.Statement;
import java.util.List;
import java.util.Queue;

/**
 *
 * @author Iman Jahandideh
 */
public class ActorRunTimeState extends AbstractState<ActorRunTimeState>
{
    private final Actor actor;
    
    private DiscreteState discreteState = new DiscreteState();
    private ContinuousState continuousState = new ContinuousState();
    
    
    public ActorRunTimeState(Actor actor)
    {
        this.actor = actor;
    }
    
    // NOTE: For Testing
    public DiscreteState GetDiscreteState()
    {
        return discreteState;
    }
    
    // NOTE: For Testing
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
    
    public int GetMessageQueueSize()
    {
        return GetMessages().size();
    }
    
    public int GetMessageQueueCapacity()
    {
        return actor.GetCapacity();
    }
    
    public Actor GetActor()
    {
       return actor;
    }
    
    public Statement GetNextStatement()
    {
        return discreteState.GetNextStatement();
    }
    
    public Queue<Statement> GetStatements()
    {
        return discreteState.GetStatements();
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
    
    public void EnqueueStatement(Statement statement)
    {
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
    public ActorRunTimeState DeepCopy()
    {
        try {
            ActorRunTimeState copy = (ActorRunTimeState) clone();
            copy.continuousState = this.continuousState.DeepCopy();
            copy.discreteState = this.discreteState.DeepCopy();
            return copy;
        } catch (CloneNotSupportedException ex) {
            return null;
        }
    }

    @Override
    protected boolean InternalEquals(ActorRunTimeState other)
    {
        return this.actor == other.actor
                && this.discreteState.equals(other.discreteState)
                && this.continuousState.equals(other.continuousState);
    }

    @Override
    protected int InternalHashCode()
    {
        return 1;
    }
}
