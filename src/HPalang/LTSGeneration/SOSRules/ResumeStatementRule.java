/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.SOSRules;

import HPalang.Core.Statements.ResumeStatement;
import HPalang.LTSGeneration.RunTimeStates.SoftwareActorState;

/**
 *
 * @author Iman Jahandideh
 */
public class ResumeStatementRule extends StatementRule<ResumeStatement>
{
    @Override
    protected Class<ResumeStatement> StatementType()
    {
        return ResumeStatement.class;
    }

    @Override
    protected void ApplyStatement(SoftwareActorState actorState, ResumeStatement statement)
    {
        actorState.SetSuspended(false);
        //actorState.FindSubState(ExecutionQueueState.class).Statements().Enqueue(actorState.SuspendedStatements());
        //actorState.SuspendedStatements().Clear();
    }
}
