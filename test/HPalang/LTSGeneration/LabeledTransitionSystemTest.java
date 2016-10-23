/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration;

import HPalang.LTSGeneration.RunTimeStates.GlobalRunTimeState;
import HPalang.Core.Actor;
import static org.hamcrest.CoreMatchers.*;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Iman Jahandideh
 */
public class LabeledTransitionSystemTest
{

    LabeledTransitionSystem transitionSystem = new LabeledTransitionSystem();
     
    @Test
    public void EmptySystemHasNoState()
    {
        LabeledTransitionSystem lts = new LabeledTransitionSystem();
        
        assertThat(lts.GetStates().size(),is(0));
    }
    
    @Test
    public void HasTheAddedState()
    {
        GlobalRunTimeState state = new GlobalRunTimeState();
        
        transitionSystem.AddState(state);

        assertThat(transitionSystem.GetStates().size(),is(1));
        assertTrue(transitionSystem.HasState(state));
    }
    
    @Test
    public void DoesNotAddDuplicatedState()
    {
        GlobalRunTimeState state = new GlobalRunTimeState();
        
        transitionSystem.AddState(state);
        transitionSystem.AddState(state.Clone());
        
        assertThat(transitionSystem.GetStates().size(),is(1));
    }
    
    @Test
    public void HasTheAddedTransition()
    {
        GlobalRunTimeState originlState = new GlobalRunTimeState();
        Label labled = new TauLabel();
        GlobalRunTimeState destinatioState = new GlobalRunTimeState();
        
        transitionSystem.AddState(originlState);
        transitionSystem.AddState(destinatioState);
        
        transitionSystem.AddTransition(originlState, labled, destinatioState);
        
        assertTrue(transitionSystem.HasTransition(originlState, labled, destinatioState));
    }
}
