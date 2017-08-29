/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.RunTimeStates;

import HPalang.Core.SoftwareActor;
import static TestUtilities.CoreUtility.CreateSofwareActor;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

/**
 *
 * @author Iman Jahandideh
 */
public class SoftwareActorStateTest
{
    @Test
    public void SoftwareActorStatesWithEqualDataAreEqual()
    {
        SoftwareActor actor = CreateSofwareActor("actor");
        
        SoftwareActorState state1 = new SoftwareActorState(actor);
        state1.SetSuspended(true);
        
        SoftwareActorState state2 = new SoftwareActorState(actor);
        state2.SetSuspended(true);
        
        assertThat(state1, is(equalTo(state2)));
    }

    @Test
    public void SofwareActorStatesWithDifferentActorsAreNotEqual()
    {
        SoftwareActor actor1 = CreateSofwareActor("actor1");
        SoftwareActor actor2 = CreateSofwareActor("actor2");
        assertThat(actor1, is(not(equalTo(actor2))));

        SoftwareActorState state1 = new SoftwareActorState(actor1);
        SoftwareActorState state2 = new SoftwareActorState(actor2);
        
        assertThat(state1, is(not(equalTo(state2))));
    }
    
    @Test
    public void SoftwareActorStatesWithDifferentSuspensionAreNotEqual()
    {
        SoftwareActor actor = CreateSofwareActor("actor");
             
        SoftwareActorState state1 = new SoftwareActorState(actor);
        state1.SetSuspended(true);
        
        SoftwareActorState state2 = new SoftwareActorState(actor);
        state2.SetSuspended(false);
        
        assertThat(state1, is(not(equalTo(state2))));
    }
    
    @Test
    public void CloneIsCorrect()
    {
        SoftwareActor actor = CreateSofwareActor("actor");
        SoftwareActorState original = new SoftwareActorState(actor);
        original.SetSuspended(true);
        
        
        SoftwareActorState copy = original.DeepCopy();
        
        assertThat(original, is(equalTo(copy)));
    }
}
