/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.HybridAutomataGeneration;

import HPalang.LTSGeneration.RunTimeStates.GlobalRunTimeState;
import HPalang.LTSGeneration.StateInfo;

/**
 *
 * @author Iman Jahandideh
 */
public interface SOSRule
{
    public void TryApply(StateInfo globalStateInfo,  HybridAutomatonGenerator generator); 
    public void TryApply(HPalang.Core.TransitionSystem.Transition<GlobalRunTimeState> transition, HybridAutomatonGenerator generator);
}
