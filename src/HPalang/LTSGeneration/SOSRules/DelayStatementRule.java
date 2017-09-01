/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.SOSRules;

import HPalang.Core.SoftwareActor;
import HPalang.Core.DifferentialEquation;
import HPalang.LTSGeneration.RunTimeStates.SoftwareActorState;
import HPalang.LTSGeneration.RunTimeStates.ContinuousBehavior;
import HPalang.LTSGeneration.Labels.SoftwareLabel;
import HPalang.Core.Statements.DelayStatement;
import HPalang.Core.Statements.ResumeStatement;
import HPalang.Core.Statement;
import HPalang.Core.Variables.RealVariable;
import HPalang.LTSGeneration.RunTimeStates.Event.ResumeSoftwareActorAction;
import HPalang.LTSGeneration.RunTimeStates.GlobalRunTimeState;

/**
 *
 * @author Iman Jahandideh
 */
public class DelayStatementRule extends SoftwareStatementRule<DelayStatement>
{
    @Override
    protected Class<DelayStatement> StatementType()
    {
        return DelayStatement.class;
    }

    @Override
    protected void ApplyStatement(SoftwareActorState actorState, DelayStatement statement, GlobalRunTimeState newGlobalState)
    {
        newGlobalState.EventsState().RegisterEvent(
                statement.GetDelay(),
                new ResumeSoftwareActorAction(actorState.SActor()));
    }
    
    
}
