/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.HybridAutomataGeneration;

import HPalang.HybridAutomataGeneration.Mocks.SOSRuleMonitor;
import HPalang.Core.TransitionSystem.LabeledTransitionSystem;
import HPalang.LTSGeneration.RunTimeStates.GlobalRunTimeState;
import HPalang.LTSGeneration.Labels.SoftwareLabel;
import HPalang.LTSGeneration.StateInfo;
import HPalang.Core.TransitionSystem.Transition;

import TestUtilities.CoreUtility;
import static org.hamcrest.CoreMatchers.*;
import org.junit.Test;
import static org.junit.Assert.*;
import static TestUtilities.CoreUtility.CreateGlobalState;
import static TestUtilities.CoreUtility.CreateSoftwareActorState;
import HPalang.Core.TransitionSystem.LTSState;

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
        
        LabeledTransitionSystem<GlobalRunTimeState> lts = new LabeledTransitionSystem<>();
          
        GlobalRunTimeState state1 = CoreUtility.CreateGlobalState();
        state1.DiscreteState().AddSoftwareActorState(CreateSoftwareActorState("1"));
        
        GlobalRunTimeState state2 = CoreUtility.CreateGlobalState();
        state2.DiscreteState().AddSoftwareActorState(CreateSoftwareActorState("1"));

        LTSState<GlobalRunTimeState> ltsState1=  lts.TryAddState(state1);
        LTSState<GlobalRunTimeState> ltsState2 = lts.TryAddState(state2);
        lts.SetInitialState(ltsState1.InnerState());
        
        lts.AddTransition(ltsState1, new SoftwareLabel(), ltsState2);
        lts.AddTransition(ltsState1, new SoftwareLabel(), ltsState2);
        
        SOSRuleMonitor rule = new SOSRuleMonitor();
        
        generator.AddSOSRule(rule);
        
        generator.Generate(lts, null);
        
        StateInfo stateInfo = new StateInfo(state1, ltsState1.InTransitions(), ltsState1.OutTransitions());
        
        assertThat(rule.appliedStateInfos,hasItem(stateInfo));
    }
  
}
