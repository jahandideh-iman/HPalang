/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Mocks;

import HPalang.LTSGeneration.RunTimeStates.SoftwareActorState;
import HPalang.LTSGeneration.RunTimeStates.GlobalRunTimeState;
import HPalang.LTSGeneration.LTSGenerator;
import HPalang.LTSGeneration.LTSGenerator;
import HPalang.Core.TransitionSystem.Label;
import HPalang.LTSGeneration.SOSRule;
import HPalang.LTSGeneration.StateInfo;
import HPalang.LTSGeneration.TransitionCollector;

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
    public void TryApply(StateInfo stateInfo, TransitionCollector generator)
    {
        generator.AddTransition(transitonLabel, transitionState);
    }

     
}
