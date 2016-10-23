/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.SOSRules;

import HPalang.LTSGeneration.LTSGenerator;
import HPalang.LTSGeneration.RunTimeStates.ActorRunTimeState;
import HPalang.LTSGeneration.RunTimeStates.GlobalRunTimeState;

/**
 *
 * @author Iman Jahandideh
 */
public abstract class ActorLevelRule implements SOSRule
{

    @Override
    public void TryApply(GlobalRunTimeState globalState, LTSGenerator generator)
    {
        for(ActorRunTimeState actorState : globalState.GetActorStates())
        {
            if(IsRuleSatisfied(actorState, globalState))
                ApplyToActorState(actorState, globalState, generator);
        }
    }

    protected abstract boolean IsRuleSatisfied(ActorRunTimeState actorState, GlobalRunTimeState globalState);

    protected abstract void ApplyToActorState(ActorRunTimeState actorState, GlobalRunTimeState globalState, LTSGenerator generator);
    
}
