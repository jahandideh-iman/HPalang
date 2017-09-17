/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration;

import HPalang.LTSGeneration.Labels.SoftwareLabel;
import HPalang.LTSGeneration.RunTimeStates.GlobalRunTimeState;
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
        
        assertThat(lts.States().size(),is(0));
    }
    
    @Test
    public void HasTheAddedState()
    {
        GlobalRunTimeState state = new GlobalRunTimeState();
        
        transitionSystem.AddState(state);

        assertThat(transitionSystem.States().size(),is(1));
        assertTrue(transitionSystem.HasState(state));
    }
    
    @Test
    public void DoesNotAddDuplicateState()
    {
        GlobalRunTimeState state = new GlobalRunTimeState();
        
        transitionSystem.AddState(state);
        transitionSystem.AddState(state.DeepCopy());
        
        assertThat(transitionSystem.States().size(),is(1));
    }
    
    @Test
    public void HasTheAddedTransition()
    {
        GlobalRunTimeState originState = new GlobalRunTimeState();
        Label labled = new SoftwareLabel();
        GlobalRunTimeState destinatioState = new GlobalRunTimeState();
        
        transitionSystem.AddState(originState);
        transitionSystem.AddState(destinatioState);
        
        transitionSystem.AddTransition(originState, labled, destinatioState);
        
        assertTrue(transitionSystem.HasTransition(originState, labled, destinatioState));
    }
    
        @Test
    public void DoesNotAddDuplicateTransition()
    {
        GlobalRunTimeState originState = new GlobalRunTimeState();
        Label labled = new SoftwareLabel();
        GlobalRunTimeState destinatioState = new GlobalRunTimeState();
        
        transitionSystem.AddState(originState);
        transitionSystem.AddState(destinatioState);
        
        transitionSystem.AddTransition(originState, labled, destinatioState);
        transitionSystem.AddTransition(originState, labled, destinatioState);
        
        assertThat(transitionSystem.Transitions().size(), equalTo(1));
    }
}
