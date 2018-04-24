/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration;

import HPalang.Core.TransitionSystem.Label;
import HPalang.Core.TransitionSystem.Transition;
import HPalang.Core.TransitionSystem.LabeledTransitionSystem;
import HPalang.LTSGeneration.Labels.SoftwareLabel;
import HPalang.LTSGeneration.RunTimeStates.GlobalRunTimeState;
import Mocks.LabelMock;
import static TestUtilities.CoreUtility.*;
import java.util.Collection;
import java.util.List;
import static org.hamcrest.CoreMatchers.*;
import org.junit.Test;
import static org.junit.Assert.*;
import HPalang.Core.TransitionSystem.StateWrapper;

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
        
        transitionSystem.TryAddState(state);

        assertThat(transitionSystem.States().size(),is(1));
        assertTrue(transitionSystem.HasState(state));
    }
    
    @Test
    public void DoesNotAddDuplicateState()
    {
        GlobalRunTimeState state = new GlobalRunTimeState();
        
        transitionSystem.TryAddState(state);
        transitionSystem.TryAddState(state.DeepCopy());
        
        assertThat(transitionSystem.States().size(),is(1));
    }
    
    @Test
    public void HasTheAddedTransition()
    {
        GlobalRunTimeState originGS = new GlobalRunTimeState();
        Label labled = new SoftwareLabel();
        GlobalRunTimeState destinationGS = new GlobalRunTimeState();
        
        StateWrapper<GlobalRunTimeState> originState = transitionSystem.TryAddState(originGS);
        StateWrapper<GlobalRunTimeState> destinatioState = transitionSystem.TryAddState(destinationGS);
        
        transitionSystem.AddTransition(originState, labled, destinatioState);
        
        assertTrue(transitionSystem.HasTransition(originState, labled, destinatioState));
    }
    
        @Test
    public void DoesNotAddDuplicateTransition()
    {
        GlobalRunTimeState originGS = new GlobalRunTimeState();
        Label label = new SoftwareLabel();
        GlobalRunTimeState destinationGS = new GlobalRunTimeState();
        
        StateWrapper<GlobalRunTimeState> originState = transitionSystem.TryAddState(originGS);
        StateWrapper<GlobalRunTimeState> destinatioState = transitionSystem.TryAddState(destinationGS);
        
        transitionSystem.AddTransition(originState, label, destinatioState);
        transitionSystem.AddTransition(originState, label, destinatioState);
        
        assertThat(transitionSystem.Transitions().size(), equalTo(1));
    }
    
    @Test
    public void ComputesOutTransitionsCorrectly()
    {
        StateWrapper<GlobalRunTimeState> gs1 = transitionSystem.TryAddState(CreateUniqueGlobalState("gs1"));      
        StateWrapper<GlobalRunTimeState> gs2 = transitionSystem.TryAddState(CreateUniqueGlobalState("gs2"));
        StateWrapper<GlobalRunTimeState> gs3 = transitionSystem.TryAddState(CreateUniqueGlobalState("gs3"));

        
        transitionSystem.AddTransition(gs1, CreateLabel("1-2"), gs2);   
        transitionSystem.AddTransition(gs1, CreateLabel("1-3"), gs3);

        Collection<Transition<GlobalRunTimeState>> outs = gs1.OutTransitions();
        
        assertThat(outs.size(), equalTo(2));
        assertThat(outs, hasItem(equalTo(new Transition(gs1,  CreateLabel("1-2"), gs2))));
        assertThat(outs, hasItem(equalTo(new Transition(gs1,  CreateLabel("1-3"), gs3))));
    }
    
    @Test
    public void ComputesInTransitionsCorrectly()
    {
        StateWrapper<GlobalRunTimeState> gs1 = transitionSystem.TryAddState(CreateUniqueGlobalState("gs1"));      
        StateWrapper<GlobalRunTimeState> gs2 = transitionSystem.TryAddState(CreateUniqueGlobalState("gs2"));
        StateWrapper<GlobalRunTimeState> gs3 = transitionSystem.TryAddState(CreateUniqueGlobalState("gs3"));

        transitionSystem.TryAddState(gs1);
        transitionSystem.TryAddState(gs2);
        transitionSystem.TryAddState(gs3);
        
        transitionSystem.AddTransition(gs2, CreateLabel("2-1"), gs1);   
        transitionSystem.AddTransition(gs3, CreateLabel("3-1"), gs1);

        Collection<Transition<GlobalRunTimeState>> ins = gs1.InTransitions();
        
        assertThat(ins.size(), equalTo(2));
        assertThat(ins, hasItem(equalTo(new Transition(gs2,  CreateLabel("2-1"), gs1))));
        assertThat(ins, hasItem(equalTo(new Transition(gs3,  CreateLabel("3-1"), gs1))));
    }
    

}
