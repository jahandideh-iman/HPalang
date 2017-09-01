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
public class PhysicalActorState extends ActorState<PhysicalActorState>
{
    
    private Mode mode = Mode.None();
    
    public PhysicalActorState(PhysicalActor actor)
    {
       super(actor);
    }
    
    @Override
    protected PhysicalActorState NewInstance()
    {
        return new PhysicalActorState(PActor());
    }

    public PhysicalActor PActor()
    {
        return (PhysicalActor) Actor();
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
        return  super.DataEquals(other) &&
                this.mode.equals(other.mode);
    }

}
