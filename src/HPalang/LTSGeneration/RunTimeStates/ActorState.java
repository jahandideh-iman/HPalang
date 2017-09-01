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
    
    public ActorState(Actor actor)
    {
        this.actor = actor;
    }
    
    public Actor Actor()
    {
        return actor;
    }
    
    public ExecutionQueueState ExecutionQueueState()
    {
        return FindSubState(ExecutionQueueState.class);
    }

    @Override
    protected boolean DataEquals(T other)
    {
        return this.actor.equals(other.Actor());
    }
    
    
}
