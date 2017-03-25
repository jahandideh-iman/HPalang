/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.SOSRules;

import HPalang.Core.Actor;
import HPalang.Core.ContinuousExpressions.ConstantExpression;
import HPalang.LTSGeneration.RunTimeStates.ActorRunTimeState;
import HPalang.LTSGeneration.RunTimeStates.ContinuousBehavior;
import HPalang.LTSGeneration.TauLabel;
import HPalang.Core.Statements.DelayStatement;
import HPalang.Core.Statements.ResumeStatement;
import HPalang.Core.Statement;
import HPalang.LTSGeneration.Reset;

/**
 *
 * @author Iman Jahandideh
 */
public class DelayStatementRule extends StatementRule<DelayStatement>
{
    @Override
    protected Class<DelayStatement> StatementType()
    {
        return DelayStatement.class;
    }

    @Override
    protected void ApplyStatement(ActorRunTimeState actorState, DelayStatement statement)
    {
        String delayVar = actorState.GetActor().GetDelayVariable().Name();
        ContinuousBehavior behavior = CreateDelayBehavior(actorState.GetActor(), statement.GetDelay(), delayVar);
        actorState.SuspendedStatements().Enqueue(actorState.StatementQueue());
        actorState.StatementQueue().Clear();
        actorState.SetSuspended(true);
        actorState.ContinuousBehaviors().Add(behavior);
    }
    
    @Override
    protected TauLabel CreateTransitionLabel(ActorRunTimeState actorState, DelayStatement statement)
    {
        return new TauLabel(Reset.ResetsFrom(new Reset(actorState.GetActor().GetDelayVariable(), new ConstantExpression(0f))));
    }
    
    private ContinuousBehavior CreateDelayBehavior(Actor actor,float delay, String delayVar)
    {
        return new ContinuousBehavior(delayVar+"<="+delay,delayVar+"'=1",delayVar+"=="+delay,Statement.StatementsFrom(new ResumeStatement()));
    }
}