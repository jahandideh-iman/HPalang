/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration;

import HPalang.Core.TransitionSystem.Label;
import HPalang.Core.TransitionSystem.SimpleLTSState;
import HPalang.Core.TransitionSystem.Transition;
import HPalang.LTSGeneration.RunTimeStates.GlobalRunTimeState;
import static TestUtilities.CoreUtility.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import org.junit.Test;
import static org.junit.Assert.*;
import HPalang.Core.TransitionSystem.StateWrapper;

/**
 *
 * @author Iman Jahandideh
 */
public class TransitionTest
{
    @Test
    public void TransitionsWithEqualDataAreEqual()
    {
        StateWrapper<GlobalRunTimeState> origin = new SimpleLTSState<>(CreateUniqueGlobalState("origin"));
        StateWrapper<GlobalRunTimeState> destination = new SimpleLTSState<>(CreateUniqueGlobalState("destination"));
        Label label = CreateLabel("label");

        Transition tr1 = new Transition(origin, label, destination);  
        Transition tr2 = new Transition(origin, label, destination);

        assertThat(tr1,is(equalTo(tr2)));
        assertThat(tr1.hashCode(),is(equalTo(tr2.hashCode())));
    }
}
