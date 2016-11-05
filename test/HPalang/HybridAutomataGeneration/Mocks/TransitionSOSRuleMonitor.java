/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.HybridAutomataGeneration.Mocks;

import HPalang.HybridAutomataGeneration.HybridAutomatonGenerator;
import HPalang.HybridAutomataGeneration.SOSRules.TransitionSOSRule;
import HPalang.LTSGeneration.LabeledTransitionSystem;
import HPalang.LTSGeneration.Transition;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Iman Jahandideh
 */
public class TransitionSOSRuleMonitor implements TransitionSOSRule
{

    public List<Transition> appliedTransitions = new LinkedList<>();


    @Override
    public void TryApply(Transition transition, HybridAutomatonGenerator generator)
    {
        appliedTransitions.add(transition);
    }
    
}
