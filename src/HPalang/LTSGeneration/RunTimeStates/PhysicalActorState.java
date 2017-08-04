/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.RunTimeStates;

import HPalang.LTSGeneration.CompositeStateT;
import HPalang.Core.PhysicalActor;

/**
 *
 * @author Iman Jahandideh
 */
public class PhysicalActorState extends  CompositeStateT<PhysicalActorState>
{
    private final PhysicalActor actor;
    
    public PhysicalActorState(PhysicalActor actor)
    {
        this.actor = actor;
    }
    
    @Override
    protected PhysicalActorState NewInstance()
    {
        return new PhysicalActorState(actor);
    }

    public PhysicalActor Actor()
    {
        return actor;
    }
    @Override
    protected void CloneData(PhysicalActorState copy)
    {
        
    }

    @Override
    protected boolean DataEquals(PhysicalActorState other)
    {
        return this.actor.equals(other.actor);
    }

    public ExecutionQueueState ExecutionQueueState()
    {
        return FindSubState(ExecutionQueueState.class);
    }

    
}
