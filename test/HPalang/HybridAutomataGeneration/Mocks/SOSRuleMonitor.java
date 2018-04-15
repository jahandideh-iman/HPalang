/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.HybridAutomataGeneration.Mocks;

import HPalang.HybridAutomataGeneration.HybridAutomatonGenerator;
import HPalang.HybridAutomataGeneration.SOSRule;
import HPalang.Core.TransitionSystem.LabeledTransitionSystem;
import HPalang.LTSGeneration.StateInfo;
import HPalang.Core.TransitionSystem.Transition;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Iman Jahandideh
 */
public class SOSRuleMonitor implements SOSRule
{
    public List<StateInfo> appliedStateInfos = new LinkedList<>();

    @Override
    public void TryApply(StateInfo globalStateInfo, HybridAutomatonGenerator generator)
    {
        appliedStateInfos.add(globalStateInfo);
    }

    @Override
    public void TryApply(Transition transition, HybridAutomatonGenerator generator)
    {
        
    }
    
}
