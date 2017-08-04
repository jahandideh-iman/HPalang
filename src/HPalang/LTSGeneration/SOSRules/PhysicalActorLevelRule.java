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

/**
 *
 * @author Iman Jahandideh
 */
public abstract class PhysicalActorLevelRule implements SOSRule
{

    @Override
    public void TryApply(GlobalRunTimeState globalState, LTSGenerator generator)
    {
        for(PhysicalActorState actorState : globalState.ContinuousState().ActorStates())
        {
            if(IsRuleSatisfied(actorState, globalState))
                ApplyToActorState(actorState, globalState, generator);
        }
    }

    protected abstract boolean IsRuleSatisfied(PhysicalActorState actorState, GlobalRunTimeState globalState);

    protected abstract void ApplyToActorState(PhysicalActorState actorState, GlobalRunTimeState globalState, LTSGenerator generator);
    
}
