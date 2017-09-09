/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration;

import HPalang.LTSGeneration.Labels.SoftwareLabel;
import HPalang.LTSGeneration.RunTimeStates.GlobalRunTimeState;
import HPalang.LTSGeneration.RunTimeStates.SoftwareActorState;
import HPalang.Core.SoftwareActor;
import Mocks.SOSRuleMock;
import Mocks.SOSRuleMonitor;
import static org.hamcrest.CoreMatchers.*;
import org.junit.Test;
import static org.junit.Assert.*;
import static TestUtilities.CoreUtility.*;

/**
 *
 * @author Iman Jahandideh
 */
public class LTSGeneratorTest
{     
    LTSGenerator ltsGenerator = new LTSGenerator();
      
    @Test
    public void InitialGlobalStateIsGivenToSOSRules()
    {
        GlobalRunTimeState initialState = CreateGlobalState();
        
        SOSRuleMonitor rule1 = new SOSRuleMonitor();        
        SOSRuleMonitor rule2 = new SOSRuleMonitor();
        
        ltsGenerator.AddSOSRule(rule1);
        ltsGenerator.AddSOSRule(rule2);
        
        LabeledTransitionSystem lts = ltsGenerator.Generate(initialState);
        
        assertThat(rule1.appliedStates,hasItem(equalTo(lts.GetInitialState())));
        assertThat(rule2.appliedStates,hasItem(equalTo(lts.GetInitialState())));
    }
    
    @Test
    public void TransitionCanBeAddedDuringGeneration()
    {
        GlobalRunTimeState initialState = CreateGlobalState();
        
        GlobalRunTimeState transitionState = CreateGlobalState();
        transitionState.DiscreteState().AddSoftwareActorState(new SoftwareActorState(CreateSofwareActor("actor")));
        
        SOSRuleMock rule = new SOSRuleMock(new SoftwareLabel(),transitionState);        
        
        ltsGenerator.AddSOSRule(rule);

        LabeledTransitionSystem lts = ltsGenerator.Generate(initialState);
        
        assertTrue(lts.HasTransition(lts.GetInitialState(),rule.transitonLabel,rule.transitionState));
    }
    
}
