/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.HybridAutomataGeneration;

import HPalang.HybridAutomataGeneration.Mocks.TransitionSOSRuleMonitor;
import HPalang.LTSGeneration.LabeledTransitionSystem;
import HPalang.LTSGeneration.RunTimeStates.GlobalRunTimeState;
import HPalang.LTSGeneration.Labels.SoftwareLabel;
import HPalang.LTSGeneration.Transition;
import TestUtilities.CoreUtility;
import static org.hamcrest.CoreMatchers.*;
import org.junit.Test;
import static org.junit.Assert.*;
import static TestUtilities.CoreUtility.CreateGlobalState;
import static TestUtilities.CoreUtility.CreateSoftwareActorState;

/**
 *
 * @author Iman Jahandideh
 */
public class HybridAutomatonGeneratorTest
{
    @Test
    public void TransitionsAreGivenToSOSRules()
    {
        HybridAutomatonGenerator generator = new HybridAutomatonGenerator();
        
        LabeledTransitionSystem lts = new LabeledTransitionSystem();
          
        GlobalRunTimeState state1 = CoreUtility.CreateGlobalState();
        state1.DiscreteState().AddSoftwareActorState(CreateSoftwareActorState("1"));
        
        GlobalRunTimeState state2 = CoreUtility.CreateGlobalState();
        state2.DiscreteState().AddSoftwareActorState(CreateSoftwareActorState("1"));

        lts.AddState(state1);
        lts.AddState(state2);
        lts.SetInitialState(state1);
        
        lts.AddTransition(state1, new SoftwareLabel(), state2);
        lts.AddTransition(state2, new SoftwareLabel(), state1);
        
        TransitionSOSRuleMonitor rule = new TransitionSOSRuleMonitor();
        
        generator.AddSOSRule(rule);
        
        generator.Generate(lts);
        
        assertThat(rule.appliedTransitions,hasItem(new Transition(state1, new SoftwareLabel(), state2)));
    }
  
}
