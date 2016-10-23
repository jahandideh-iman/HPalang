/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.SOSRules;

import HPalang.LTSGeneration.RunTimeStates.ActorRunTimeState;
import HPalang.LTSGeneration.RunTimeStates.GlobalRunTimeState;
import HPalang.LTSGeneration.LTSGenerator;
import HPalang.LTSGeneration.LTSGenerator;

/**
 *
 * @author Iman Jahandideh
 */
public interface SOSRule
{
    public void TryApply(GlobalRunTimeState globalState, LTSGenerator generator); 
}
