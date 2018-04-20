/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.RunTimeStates;

import HPalang.Core.Actor;
import HPalang.Core.PhysicalActor;
import HPalang.LTSGeneration.CompositeStateT;

/**
 *
 * @author Iman Jahandideh
 */
public abstract class ActorState<T extends ActorState> extends  CompositeStateT<T>
{
    private final Actor actor;
    protected boolean isSuspended = false;
    
    
    public ActorState(Actor actor)
    {
        this.actor = actor;
    }
    
    public Actor Actor()
    {
        return actor;
    }
    
    public boolean IsSuspended()
    {
        return isSuspended;
    }
    
    public abstract void SetSuspended(boolean suspended);
    
    public ExecutionQueueState ExecutionQueueState()
    {
        return FindSubState(ExecutionQueueState.class);
    }
    
    public MessageQueueState MessageQueueState()
    {
        return FindSubState(MessageQueueState.class);
    }
    
    public ValuationState ValuationState()
    {
        return FindSubState(ValuationState.class);
    }

    @Override
    protected boolean DataEquals(T other)
    {
        return this.isSuspended == other.IsSuspended() &&
                this.actor.equals(other.Actor());
    }

    @Override
    protected int DataHashCode()
    {
        return actor.hashCode() + Boolean.hashCode(isSuspended);
    }
  
}
