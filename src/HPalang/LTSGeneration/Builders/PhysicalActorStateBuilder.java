/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.Builders;

import HPalang.Core.Mode;
import HPalang.Core.PhysicalActor;
import HPalang.Core.ValuationContainers.NullValutationContainer;
import HPalang.LTSGeneration.RunTimeStates.ExecutionQueueState;
import HPalang.LTSGeneration.RunTimeStates.PhysicalActorState;
import HPalang.LTSGeneration.RunTimeStates.ValuationState;
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
    
    
    public PhysicalActorStateBuilder WithActor(PhysicalActor actor)
    {
        this.actor = actor;
        return this;
    }
       
    public PhysicalActorStateBuilder WithMode(Mode mode)
    {
        this.mode = mode;
        return this;
    }
    
    public PhysicalActorState Build()
    {
        PhysicalActorState state = new PhysicalActorState(actor);
        
        state.AddSubstate(new ValuationState( new NullValutationContainer()));
        
        state.SetMode(mode);
        
        state.AddSubstate(new ExecutionQueueState());
        
        
        return state;
    }

    
}
