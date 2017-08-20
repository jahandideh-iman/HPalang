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
import HPalang.LTSGeneration.RunTimeStates.PhysicalActorState;
import HPalang.LTSGeneration.StateInfo;
import HPalang.LTSGeneration.Transition;
import HPalang.LTSGeneration.TransitionCollector;
import java.util.Collection;

/**
 *
 * @author Iman Jahandideh
 */
public abstract class PhysicalActorLevelRule implements SOSRule
{

    @Override
    public void TryApply(StateInfo globalStateInfo, TransitionCollector collector)
    {
        for(PhysicalActorState actorState : globalStateInfo.State().ContinuousState().ActorStates())
        {
            if(IsRuleSatisfied(actorState, globalStateInfo))
                ApplyToActorState(actorState, globalStateInfo.State(), collector);
        }
    }
    
    protected abstract boolean IsRuleSatisfied(PhysicalActorState actorState, StateInfo globalStateInfo);

    protected abstract void ApplyToActorState(PhysicalActorState actorState, GlobalRunTimeState globalState, TransitionCollector collector);
    
}
