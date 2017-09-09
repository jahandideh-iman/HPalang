/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.SOSRules;

import HPalang.LTSGeneration.RunTimeStates.ActorState;
import HPalang.LTSGeneration.RunTimeStates.GlobalRunTimeState;
import HPalang.LTSGeneration.RunTimeStates.PhysicalActorState;
import HPalang.LTSGeneration.RunTimeStates.SoftwareActorState;
import HPalang.LTSGeneration.SOSRule;
import HPalang.LTSGeneration.StateInfo;
import HPalang.LTSGeneration.TransitionCollector;

/**
 *
 * @author Iman Jahandideh
 */
public abstract class ActorLevelRule  implements SOSRule
{

    @Override
    public void TryApply(StateInfo stateInfo, TransitionCollector collector)
    {
        for(SoftwareActorState actorState : stateInfo.State().DiscreteState().ActorStates())
        {
            if(IsRuleSatisfied(actorState, stateInfo.State()))
                ApplyToActorState(actorState, stateInfo.State(), collector);
        }
        
        for(PhysicalActorState actorState : stateInfo.State().ContinuousState().ActorStates())
        {
            if(IsRuleSatisfied(actorState, stateInfo.State()))
                ApplyToActorState(actorState, stateInfo.State(), collector);
        }
    }
    
    protected abstract boolean IsRuleSatisfied(ActorState actorState, GlobalRunTimeState globalState);

    protected abstract void ApplyToActorState(ActorState actorState, GlobalRunTimeState globalState, TransitionCollector collector);
    
}
