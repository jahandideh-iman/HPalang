/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.SOSRules;

import HPalang.Core.ContinuousExpressions.ConstantContinuousExpression;
import HPalang.LTSGeneration.RunTimeStates.SoftwareActorState;
import HPalang.LTSGeneration.Labels.SoftwareLabel;
import HPalang.Core.Statements.DelayStatement;
import HPalang.LTSGeneration.Labels.Reset;
import HPalang.LTSGeneration.RunTimeStates.Event.Event;
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
    protected void ApplyStatement(SoftwareActorState newActorState, DelayStatement statement, GlobalRunTimeState newGlobalState)
    {
        newActorState.SetSuspended(true);
        newGlobalState.EventsState().RegisterEvent(statement.GetDelay(),
                new ResumeSoftwareActorAction(newActorState.SActor()));
    }

    @Override
    protected SoftwareLabel CreateTransitionLabel(SoftwareActorState actorState, DelayStatement statement, GlobalRunTimeState globalState)
    {
        Event event =  globalState.EventsState().RegisterEvent(statement.GetDelay(),
                new ResumeSoftwareActorAction(actorState.SActor()));
        
        Reset reset = new Reset(event.Timer(), new ConstantContinuousExpression(0));
        return new SoftwareLabel(Reset.From(reset));
    }

    @Override
    protected boolean MustGoToDeadlock(GlobalRunTimeState globalState, DelayStatement statement)
    {
        return globalState.EventsState().PoolState().Pool().HasAnyAvailableVariable() == false;
    }
    
    
}
