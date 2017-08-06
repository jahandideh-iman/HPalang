/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.RunTimeStates;

import HPalang.Core.Mode;
import HPalang.LTSGeneration.CompositeStateT;
import HPalang.Core.PhysicalActor;

/**
 *
 * @author Iman Jahandideh
 */
public class PhysicalActorState extends  CompositeStateT<PhysicalActorState>
{
    private final PhysicalActor actor;
    private Mode mode = Mode.None();
    
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
    
    public void SetMode(Mode mode)
    {
        // TODO: Check whether mode is actually a mode in the physical actor.
        this.mode = mode;
    }
    
    public Mode Mode()
    {
        return mode;
    }

    @Override
    protected void CloneData(PhysicalActorState copy)
    {
        copy.mode = mode;
    }

    @Override
    protected boolean DataEquals(PhysicalActorState other)
    {
        return this.actor.equals(other.actor) &&
                this.mode.equals(other.mode);
    }

    public ExecutionQueueState ExecutionQueueState()
    {
        return FindSubState(ExecutionQueueState.class);
    }   


}
