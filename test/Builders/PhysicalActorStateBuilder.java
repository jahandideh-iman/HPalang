/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Builders;

import HPalang.Core.Mode;
import HPalang.Core.PhysicalActor;
import HPalang.LTSGeneration.RunTimeStates.PhysicalActorState;
import HPalang.LTSGeneration.State;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Iman Jahandideh
 */
public class PhysicalActorStateBuilder
{
    private PhysicalActor actor;
    private Mode mode;
    private List<State> substates = new LinkedList<>();
    
    public PhysicalActorState Build()
    {
        PhysicalActorState state = new PhysicalActorState(actor);
        
        state.SetMode(mode);
        
        substates.forEach(s -> state.AddSubstate(s));
        
        return state;
    }

    public PhysicalActorStateBuilder With(PhysicalActor actor)
    {
        this.actor = actor;
        return this;
    }
    
    public PhysicalActorStateBuilder With(State substate)
    {
        substates.add(substate);
        return this;
    }
    
    public PhysicalActorStateBuilder With(Mode mode)
    {
        this.mode = mode;
        return this;
    }
    
}
