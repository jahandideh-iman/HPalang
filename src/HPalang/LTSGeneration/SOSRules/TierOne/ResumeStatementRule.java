/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.SOSRules.TierOne;

import HPalang.Core.Message;
import HPalang.Core.Statements.ResumeStatement;
import HPalang.LTSGeneration.LTSGenerator;
import HPalang.LTSGeneration.RunTimeStates.ActorRunTimeState;
import HPalang.LTSGeneration.RunTimeStates.GlobalRunTimeState;
import HPalang.LTSGeneration.SOSRules.ActorLevelRule;
import HPalang.LTSGeneration.TauLabel;

/**
 *
 * @author Iman Jahandideh
 */
public class ResumeStatementRule extends ActorLevelRule
{
    @Override
    protected boolean IsRuleSatisfied(ActorRunTimeState actorState, GlobalRunTimeState globalState)
    {
        return actorState.StatementQueue().Head().Is(ResumeStatement.class);
    }

    @Override
    protected void ApplyToActorState(ActorRunTimeState actorState, GlobalRunTimeState globalState, LTSGenerator generator)
    {
        GlobalRunTimeState nextGlobalState = globalState.DeepCopy();
        ActorRunTimeState nextActorState = nextGlobalState.FindActorState(actorState.GetActor());
        
        nextActorState.StatementQueue().Dequeue();
        nextActorState.SetSuspended(false);
        nextActorState.StatementQueue().Enqueue(actorState.SuspendedStatements());
        nextActorState.SuspendedStatements().Clear();
        
        generator.AddTransition(new TauLabel(), nextGlobalState);
    }
}
