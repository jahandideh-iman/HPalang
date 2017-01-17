/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.SOSRules;

import HPalang.Core.Actor;
import HPalang.LTSGeneration.RunTimeStates.ActorRunTimeState;
import HPalang.LTSGeneration.RunTimeStates.ContinuousBehavior;
import HPalang.LTSGeneration.RunTimeStates.GlobalRunTimeState;
import HPalang.LTSGeneration.LTSGenerator;
import HPalang.LTSGeneration.TauLabel;
import HPalang.Core.Statements.DelayStatement;
import HPalang.Core.Statements.ResumeStatement;
import HPalang.Core.Statement;

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
        String delayVar = actorState.GetActor().GetDelayVariableName();
        ContinuousBehavior behavior = CreateDelayBehavior(actorState.GetActor(), statement.GetDelay(), delayVar);
        actorState.SuspendedStatements().Enqueue(actorState.StatementQueue());
        actorState.SuspendedStatements().Clear();
        actorState.SetSuspended(true);
        actorState.ContinuousBehaviors().Add(behavior);
    }
    
    private ContinuousBehavior CreateDelayBehavior(Actor actor,float delay, String delayVar)
    {
        return new ContinuousBehavior(delayVar+"<="+delay,delayVar+"'=1",delayVar+"=="+delay,Statement.StatementsFrom(new ResumeStatement()));
    }
}
