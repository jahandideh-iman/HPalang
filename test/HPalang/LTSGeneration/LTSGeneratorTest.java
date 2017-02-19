/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration;

import HPalang.LTSGeneration.RunTimeStates.ActorRunTimeState;
import Builders.GlobalRunTimeStateBuilder;
import HPalang.Core.Actor;
import Mocks.SOSRuleMock;
import Mocks.SOSRuleMonitor;
import static org.hamcrest.CoreMatchers.*;
import org.junit.Test;
import static org.junit.Assert.*;

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
        GlobalRunTimeState initialState = new GlobalRunTimeStateBuilder().Build();
        
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
        GlobalRunTimeState initialState = new GlobalRunTimeStateBuilder().Build();
        
        GlobalRunTimeState transitionState = new GlobalRunTimeState();
        transitionState.AddActorRunTimeState(new ActorRunTimeState(new Actor("actor",0)));
        
        SOSRuleMock rule = new SOSRuleMock(new TauLabel(),transitionState);        
        
        ltsGenerator.AddSOSRule(rule);

        LabeledTransitionSystem lts = ltsGenerator.Generate(initialState);
        
        assertTrue(lts.HasTransition(lts.GetInitialState(),rule.transitonLabel,rule.transitionState));
    }
    
}
