/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.SOSRules;

import HPalang.LTSGeneration.LTSGenerator;
import HPalang.LTSGeneration.Message;
import HPalang.LTSGeneration.RunTimeStates.ActorRunTimeState;
import HPalang.LTSGeneration.RunTimeStates.GlobalRunTimeState;
import HPalang.LTSGeneration.TauLabel;
import HPalang.Statements.ContinuousBehaviorStatement;

/**
 *
 * @author Iman Jahandideh
 */
public class ContinuousBehaviorRule extends ActorLevelRule
{

    @Override
    protected boolean IsRuleSatisfied(ActorRunTimeState actorState, GlobalRunTimeState globalState)
    {
        return actorState.GetDiscreteState().IsDelayed() == false
                && actorState.GetDiscreteState().GetNextStatement() instanceof ContinuousBehaviorStatement;
    }

    @Override
    protected void ApplyToActorState(ActorRunTimeState actorState, GlobalRunTimeState globalState, LTSGenerator generator)
    {
        GlobalRunTimeState newGlobalState = globalState.DeepCopy();
        
        ActorRunTimeState newActorState = newGlobalState.FindActorState(actorState.GetActor());
        
        ContinuousBehaviorStatement behaviorStatement = (ContinuousBehaviorStatement) newActorState.GetNextStatement();
        newActorState.DequeueNextStatement();
        
        newActorState.GetContinuousState().AddBehavior(behaviorStatement.GetBehavior());
        
        generator.AddTransition(new TauLabel(), newGlobalState);
    }
    
}
