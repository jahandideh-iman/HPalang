/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration;

import HPalang.LTSGeneration.Labels.SoftwareLabel;
import HPalang.LTSGeneration.RunTimeStates.GlobalRunTimeState;
import Mocks.LabelMock;
import static TestUtilities.CoreUtility.*;
import java.util.List;
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
        Label label = new SoftwareLabel();
        GlobalRunTimeState destinatioState = new GlobalRunTimeState();
        
        transitionSystem.AddState(originState);
        transitionSystem.AddState(destinatioState);
        
        transitionSystem.AddTransition(originState, label, destinatioState);
        transitionSystem.AddTransition(originState, label, destinatioState);
        
        assertThat(transitionSystem.Transitions().size(), equalTo(1));
    }
    
    @Test
    public void ComputesOutTransitionsCorrectly()
    {
        GlobalRunTimeState gs1 = CreateUniqueGlobalState("gs1");      
        GlobalRunTimeState gs2 = CreateUniqueGlobalState("gs2");
        GlobalRunTimeState gs3 = CreateUniqueGlobalState("gs3");

        transitionSystem.AddState(gs1);
        transitionSystem.AddState(gs2);
        transitionSystem.AddState(gs3);
        
        transitionSystem.AddTransition(gs1, CreateLabel("1-2"), gs2);   
        transitionSystem.AddTransition(gs1, CreateLabel("1-3"), gs3);

        List<Transition> outs = transitionSystem.GetOutTransitionsFor(gs1);
        
        assertThat(outs.size(), equalTo(2));
        assertThat(outs, hasItem(equalTo(new Transition(gs1,  CreateLabel("1-2"), gs2))));
        assertThat(outs, hasItem(equalTo(new Transition(gs1,  CreateLabel("1-3"), gs3))));
    }
    
    @Test
    public void ComputesInTransitionsCorrectly()
    {
        GlobalRunTimeState gs1 = CreateUniqueGlobalState("gs1");      
        GlobalRunTimeState gs2 = CreateUniqueGlobalState("gs2");
        GlobalRunTimeState gs3 = CreateUniqueGlobalState("gs3");

        transitionSystem.AddState(gs1);
        transitionSystem.AddState(gs2);
        transitionSystem.AddState(gs3);
        
        transitionSystem.AddTransition(gs2, CreateLabel("2-1"), gs1);   
        transitionSystem.AddTransition(gs3, CreateLabel("3-1"), gs1);

        List<Transition> ins = transitionSystem.GetInTransitionsFor(gs1);
        
        assertThat(ins.size(), equalTo(2));
        assertThat(ins, hasItem(equalTo(new Transition(gs2,  CreateLabel("2-1"), gs1))));
        assertThat(ins, hasItem(equalTo(new Transition(gs3,  CreateLabel("3-1"), gs1))));
    }
    

}
