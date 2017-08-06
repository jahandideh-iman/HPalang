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
import HPalang.LTSGeneration.Label;
import HPalang.LTSGeneration.SOSRule;
import HPalang.LTSGeneration.StateInfo;

/**
 *
 * @author Iman Jahandideh
 */
public class SOSRuleMock implements SOSRule
{
    public GlobalRunTimeState transitionState;
    public Label transitonLabel;
    
    public SOSRuleMock(Label transitionLabel,GlobalRunTimeState transitionState)
    {
        this.transitionState = transitionState;
        this.transitonLabel = transitionLabel;
    }

    @Override
    public void TryApply(StateInfo stateInfo, LTSGenerator generator)
    {
        generator.AddTransition(transitonLabel, transitionState);
    }

     
}
