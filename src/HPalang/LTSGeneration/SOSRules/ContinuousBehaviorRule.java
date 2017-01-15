/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.SOSRules;

import HPalang.LTSGeneration.LTSGenerator;
import HPalang.Core.Message;
import HPalang.LTSGeneration.RunTimeStates.ActorRunTimeState;
import HPalang.LTSGeneration.RunTimeStates.GlobalRunTimeState;
import HPalang.LTSGeneration.TauLabel;
import HPalang.Core.Statements.ContinuousBehaviorStatement;

/**
 *
 * @author Iman Jahandideh
 */
public class ContinuousBehaviorRule extends ActorLevelRule
{

    @Override
    protected boolean IsRuleSatisfied(ActorRunTimeState actorState, GlobalRunTimeState globalState)
    {
        return actorState.IsSuspended() == false
                && actorState.StatementQueue().Head() instanceof ContinuousBehaviorStatement;
    }

    @Override
    protected void ApplyToActorState(ActorRunTimeState actorState, GlobalRunTimeState globalState, LTSGenerator generator)
    {
        GlobalRunTimeState newGlobalState = globalState.DeepCopy();
        
        ActorRunTimeState newActorState = newGlobalState.FindActorState(actorState.GetActor());
        
        ContinuousBehaviorStatement behaviorStatement = (ContinuousBehaviorStatement) newActorState.StatementQueue().Head();
        newActorState.StatementQueue().Dequeue();
        
        newActorState.ContinuousBehaviors().Add(behaviorStatement.GetBehavior());
        
        generator.AddTransition(new TauLabel(), newGlobalState);
    }
    
}
