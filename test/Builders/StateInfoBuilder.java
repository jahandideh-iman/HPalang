/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Builders;

import HPalang.LTSGeneration.RunTimeStates.GlobalRunTimeState;
import HPalang.LTSGeneration.StateInfo;
import HPalang.Core.TransitionSystem.Transition;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Iman Jahandideh
 */
public class StateInfoBuilder
{
    private GlobalRunTimeState globalState;
    private List<Transition<GlobalRunTimeState>> outTransitions = new LinkedList<>();
    public StateInfoBuilder WithState(GlobalRunTimeState globalState)
    {
        this.globalState = globalState;
        return this;
    }
    
    public StateInfoBuilder AddOutTransition(Transition<GlobalRunTimeState> tranistion)
    {
        outTransitions.add(tranistion);
        return this;
    }
    
    public StateInfo Build()
    {
        return new StateInfo(globalState, Collections.EMPTY_LIST, outTransitions);
    }
}
