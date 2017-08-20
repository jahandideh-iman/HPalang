/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.RunTimeStates;

import HPalang.Core.SoftwareActor;
import TestUtilities.Utilities;
import static TestUtilities.Utilities.CreateSoftwareActorState;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

/**
 *
 * @author Iman Jahandideh
 */
public class GlobalRunTimeStateTest
{

    @Test
    public void DeepCopyIsCorrect()
    {
        GlobalRunTimeState orignal = Utilities.CreateGlobalState();
        orignal.DiscreteState().AddSoftwareActorState(CreateSoftwareActorState("actor1"));
        orignal.DiscreteState().AddSoftwareActorState(CreateSoftwareActorState("actor2"));
        
        GlobalRunTimeState copy = (GlobalRunTimeState) orignal.DeepCopy();
        
        assertThat(copy, equalTo(orignal));
        assertThat(copy, not(sameInstance(orignal)));
        assertThat(copy.getClass(), equalTo(orignal.getClass()));
    }
    
    @Test
    public void CloneCreateNewActorState()
    {
        GlobalRunTimeState orignal = Utilities.CreateGlobalState();
        orignal.DiscreteState().AddSoftwareActorState(CreateSoftwareActorState("actor1"));
        orignal.DiscreteState().AddSoftwareActorState(CreateSoftwareActorState("actor2"));
        
        GlobalRunTimeState copy = (GlobalRunTimeState)orignal.DeepCopy();
        
        SoftwareActor actor1 = orignal.DiscreteState().ActorStates().iterator().next().Actor();
        SoftwareActor actor2 = orignal.DiscreteState().ActorStates().iterator().next().Actor();
        
        assertThat(copy.DiscreteState().FindActorState(actor1),not(sameInstance(orignal.DiscreteState().FindActorState(actor1))));
        assertThat(copy.DiscreteState().FindActorState(actor1),equalTo(orignal.DiscreteState().FindActorState(actor1)));
        
        assertThat(copy.DiscreteState().FindActorState(actor2),not(sameInstance(orignal.DiscreteState().FindActorState(actor2))));
        assertThat(copy.DiscreteState().FindActorState(actor2),equalTo(orignal.DiscreteState().FindActorState(actor2)));
    }
    
}
