/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.HybridAutomataGeneration.SOSRules;

import HPalang.HybridAutomataGeneration.HybridAutomatonGenerator;
import HPalang.LTSGeneration.Transition;


/**
 *
 * @author Iman Jahandideh
 */
public interface TransitionSOSRule
{
     public void TryApply(Transition transition, HybridAutomatonGenerator generator); 
}
