/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.RunTimeStates;

import HPalang.Core.Actor;
import HPalang.LTSGeneration.Utilities;
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
        GlobalRunTimeState orignal = Utilities.NewGlobalState(
                Utilities.NewActorState("actor1"),
                Utilities.NewActorState("actor2"));
        
        GlobalRunTimeState copy = (GlobalRunTimeState) orignal.DeepCopy();
        
        assertThat(copy, equalTo(orignal));
        assertThat(copy, not(sameInstance(orignal)));
        assertThat(copy.getClass(), equalTo(orignal.getClass()));
    }
    
    @Test
    public void CloneCreateNewActorState()
    {
        GlobalRunTimeState orignal = Utilities.NewGlobalState(
                Utilities.NewActorState("actor1"),
                Utilities.NewActorState("actor2"));
        
        GlobalRunTimeState copy = (GlobalRunTimeState)orignal.DeepCopy();
        
        Actor actor1 = orignal.GetActorStates().get(0).GetActor();
        Actor actor2 = orignal.GetActorStates().get(1).GetActor();
        
        assertThat(copy.FindActorState(actor1),not(sameInstance(orignal.FindActorState(actor1))));
        assertThat(copy.FindActorState(actor1),equalTo(orignal.FindActorState(actor1)));
        
        assertThat(copy.FindActorState(actor2),not(sameInstance(orignal.FindActorState(actor2))));
        assertThat(copy.FindActorState(actor2),equalTo(orignal.FindActorState(actor2)));
    }
    
}
