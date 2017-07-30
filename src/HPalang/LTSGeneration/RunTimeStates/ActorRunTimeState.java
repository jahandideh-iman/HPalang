/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.RunTimeStates;

import HPalang.Utilities.Queue;
import HPalang.Core.EqualitableAndClonable;
import HPalang.Core.ValuationContainer;
import HPalang.Core.Actor;
import HPalang.Core.DiscreteVariable;
import HPalang.Core.Message;
import HPalang.LTSGeneration.CompositeStateT;
import HPalang.Core.Statement;
import java.util.Map.Entry;

/**
 *
 * @author Iman Jahandideh
 */
public class ActorRunTimeState extends CompositeStateT<ActorRunTimeState>
{
    private final Actor actor;
    
    private  Queue<Message> lowPriorityMessages = new Queue<>();
    private  Queue<Message> highPriorityMessages = new Queue<>();
    
    private  Queue<Statement> statementQueue = new Queue<>();
    
    private  ContinuousBehaviorContianer continuousBehaviors = new ContinuousBehaviorContianer();

    private boolean isSuspended = false;
    private  Queue<Statement> suspendedStatements = new Queue<>();
    private  ValuationContainer valuations = new ValuationContainer();
    
    public ActorRunTimeState(Actor actor)
    {
        this.actor = actor;
        
        actor.GetDiscreteVariables().entrySet().forEach((entry) -> {
            valuations.Set(entry.getKey(), entry.getValue());
        });
        
    }
    
    public ContinuousBehaviorContianer ContinuousBehaviors()
    {
        return continuousBehaviors;
    }
    
    public Queue<Message> HighPriorityMessageQueue()
    {
        return highPriorityMessages;
    }
    
    public Queue<Message> LowPriorityMessageQueue()
    {
        return lowPriorityMessages;
    }
    
    public Queue<Statement> StatementQueue()
    {
        return statementQueue;
    }

    public int GetMessageQueueCapacity()
    {
        return actor.GetCapacity();
    }
    
    public Actor GetActor()
    {
       return actor;
    }
       
    public void SetSuspended(boolean suspended)
    {
        isSuspended = suspended;
    }
    
    public boolean IsSuspended()
    {
        return isSuspended;
    }
    
    public Queue<Statement> SuspendedStatements()
    {
        return suspendedStatements;
    }
    
    public ValuationContainer Valuations()
    {
        return valuations;
    }
    
    // TODO: Merge this with ValuationContainer
    public boolean ValuationEqual(ActorRunTimeState other)
    {
        return this.isSuspended == other.isSuspended
                && this.suspendedStatements.equals(other.suspendedStatements)
                && this.valuations.equals(other.valuations);
    }
   

   
    @Override
    protected ActorRunTimeState NewInstance()
    {
        return new ActorRunTimeState(actor);
    }

    @Override
    protected void CloneData(ActorRunTimeState copy)
    {
            copy.lowPriorityMessages = this.lowPriorityMessages.DeepCopy();
            copy.highPriorityMessages = this.highPriorityMessages.DeepCopy();
            copy.statementQueue = this.statementQueue.DeepCopy();
            copy.suspendedStatements = this.suspendedStatements.DeepCopy();
            copy.continuousBehaviors = this.continuousBehaviors.DeepCopy();
            copy.valuations = this.valuations.DeepCopy();
    }

    @Override
    protected boolean DataEquals(ActorRunTimeState other)
    {
        return this.actor == other.actor
                && this.continuousBehaviors.equals(other.continuousBehaviors)
                && this.highPriorityMessages.equals(other.highPriorityMessages)
                && this.lowPriorityMessages.equals(other.lowPriorityMessages)
                && this.statementQueue.equals(other.statementQueue)
                && this.ValuationEqual(other);
    }
}
