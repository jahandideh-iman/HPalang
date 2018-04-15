/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.SOSRules;

import HPalang.LTSGeneration.SOSRule;
import HPalang.LTSGeneration.LTSGenerator;
import HPalang.LTSGeneration.RunTimeStates.SoftwareActorState;
import HPalang.LTSGeneration.RunTimeStates.GlobalRunTimeState;
import HPalang.LTSGeneration.StateInfo;
import HPalang.Core.TransitionSystem.Transition;
import HPalang.LTSGeneration.TransitionCollector;
import java.util.Collection;

/**
 *
 * @author Iman Jahandideh
 */
public abstract class SoftwareActorLevelRule implements SOSRule
{

    @Override
    public void TryApply(StateInfo stateInfo, TransitionCollector collector)
    {
        for(SoftwareActorState actorState : stateInfo.State().DiscreteState().ActorStates())
        {
            if(IsRuleSatisfied(actorState, stateInfo.State()))
                ApplyToActorState(actorState, stateInfo.State(), collector);
        }
    }
    
    protected abstract boolean IsRuleSatisfied(SoftwareActorState actorState, GlobalRunTimeState globalState);

    protected abstract void ApplyToActorState(SoftwareActorState actorState, GlobalRunTimeState globalState, TransitionCollector collector);
    
}
