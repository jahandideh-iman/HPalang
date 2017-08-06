/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Mocks;

import HPalang.LTSGeneration.RunTimeStates.ActorRunTimeState;
import HPalang.LTSGeneration.RunTimeStates.GlobalRunTimeState;
import HPalang.LTSGeneration.LTSGenerator;
import HPalang.LTSGeneration.LTSGenerator;
import HPalang.LTSGeneration.SOSRule;
import HPalang.LTSGeneration.StateInfo;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Iman Jahandideh
 */
public class SOSRuleMonitor implements SOSRule
{
    public List<GlobalRunTimeState> appliedStates = new LinkedList<>();

    @Override
    public void TryApply(StateInfo stateInfo, LTSGenerator generator)
    {
        appliedStates.add(stateInfo.State().DeepCopy());
    }
   
}
