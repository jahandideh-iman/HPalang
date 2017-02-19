/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.HybridAutomataGeneration;

import Builders.ActorRunTimeStateBuilder;
import Builders.GlobalRunTimeStateBuilder;
import HPalang.Core.Actor;
import HPalang.HybridAutomataGeneration.Mocks.TransitionSOSRuleMonitor;
import HPalang.LTSGeneration.LabeledTransitionSystem;
import HPalang.LTSGeneration.GlobalRunTimeState;
import HPalang.LTSGeneration.TauLabel;
import HPalang.LTSGeneration.Transition;
import Mocks.SOSRuleMock;
import static org.hamcrest.CoreMatchers.*;
import org.junit.Test;
import static org.junit.Assert.*;

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
        
        
        
        GlobalRunTimeState state1 = new GlobalRunTimeStateBuilder()
                .AddActorRunTimeState(new ActorRunTimeStateBuilder().WithActor(new Actor("1",0))).Build();
        
        GlobalRunTimeState state2 = new GlobalRunTimeStateBuilder()
                .AddActorRunTimeState(new ActorRunTimeStateBuilder().WithActor(new Actor("2",0))).Build();
        
        lts.AddState(state1);
        lts.AddState(state2);
        lts.SetInitialState(state1);
        
        lts.AddTransition(state1, new TauLabel(), state2);
        lts.AddTransition(state2, new TauLabel(), state1);
        
        TransitionSOSRuleMonitor rule = new TransitionSOSRuleMonitor();
        
        generator.AddSOSRule(rule);
        
        generator.Generate(lts);
        
        assertThat(rule.appliedTransitions,hasItem(new Transition(state1, new TauLabel(), state2)));
    }
    
}
