/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.SOSRules;

import HPalang.LTSGeneration.SOSRule;
import HPalang.LTSGeneration.LTSGenerator;
import HPalang.LTSGeneration.RunTimeStates.ActorRunTimeState;
import HPalang.LTSGeneration.RunTimeStates.GlobalRunTimeState;
import HPalang.LTSGeneration.StateInfo;
import HPalang.LTSGeneration.Transition;
import HPalang.LTSGeneration.TransitionCollector;
import java.util.Collection;

/**
 *
 * @author Iman Jahandideh
 */
public abstract class ActorLevelRule implements SOSRule
{

    @Override
    public void TryApply(StateInfo stateInfo, TransitionCollector collector)
    {
        for(ActorRunTimeState actorState : stateInfo.State().GetActorStates())
        {
            if(IsRuleSatisfied(actorState, stateInfo.State()))
                ApplyToActorState(actorState, stateInfo.State(), collector);
        }
    }
    
    protected abstract boolean IsRuleSatisfied(ActorRunTimeState actorState, GlobalRunTimeState globalState);

    protected abstract void ApplyToActorState(ActorRunTimeState actorState, GlobalRunTimeState globalState, TransitionCollector collector);
    
}
