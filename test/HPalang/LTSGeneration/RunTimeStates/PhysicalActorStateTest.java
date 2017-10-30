/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.RunTimeStates;

import Builders.ModeBuilder;
import HPalang.Core.Mode;
import HPalang.Core.PhysicalActor;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import org.junit.Test;
import static org.junit.Assert.*;
import static TestUtilities.CoreUtility.*;

/**
 *
 * @author Iman Jahandideh
 */
public class PhysicalActorStateTest
{
    @Test
    public void PhysicalActorStatesWithEqualDataAreEqual()
    {
        PhysicalActor actor = CreatePhysicalActor("Actor");
        Mode mode = new ModeBuilder().Build();
        
        PhysicalActorState state1 = new PhysicalActorState(actor);
        state1.SetMode(mode);
        
        PhysicalActorState state2 = new PhysicalActorState(actor);
        state2.SetMode(mode);
        
        assertThat(state1, is(equalTo(state2)));
    }
    
    @Test
    public void PhysicalActorStatesWithDifferentActorsAreNotEqual()
    {
        PhysicalActor actor1 = CreatePhysicalActor("Actor1");    
        PhysicalActor actor2 = CreatePhysicalActor("Actor2");

        PhysicalActorState state1 = new PhysicalActorState(actor1);
        PhysicalActorState state2 = new PhysicalActorState(actor2);
        
        assertThat(state1, is(not(equalTo(state2))));
    }
    
    @Test
    public void PhysicalActorStatesWithDifferentModeAreNotEqual()
    {
        PhysicalActor actor = CreatePhysicalActor("Actor");
        
        Mode mode1 = new ModeBuilder().WithInvarient(FakeInvarient("inv1")).WithName("mode1").Build();    
        Mode mode2 = new ModeBuilder().WithInvarient(FakeInvarient("inv2")).WithName("mode2").Build();
        
        PhysicalActorState state1 = new PhysicalActorState(actor);
        state1.SetMode(mode1);
        
        PhysicalActorState state2 = new PhysicalActorState(actor);
        state2.SetMode(mode2);
        
        assertThat(state1, is(not(equalTo(state2))));
    }
    
    @Test
    public void CloneIsCorrect()
    {
        PhysicalActor actor = CreatePhysicalActor("Actor");
        
        Mode mode1 = new ModeBuilder().Build();    

        PhysicalActorState original = new PhysicalActorState(actor);
        original.SetMode(mode1);
        
        PhysicalActorState copy = original.DeepCopy();
        
        assertThat(original, is(equalTo(copy)));
    }
    
}
