/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.SOSRules;

import HPalang.Core.Actor;
import HPalang.Core.ContinuousExpressions.ConstantContinuousExpression;
import HPalang.Core.ContinuousVariable;
import HPalang.Core.DefferentialEquation;
import HPalang.LTSGeneration.RunTimeStates.ActorRunTimeState;
import HPalang.LTSGeneration.RunTimeStates.ContinuousBehavior;
import HPalang.LTSGeneration.Labels.SoftwareLabel;
import HPalang.Core.Statements.DelayStatement;
import HPalang.Core.Statements.ResumeStatement;
import HPalang.Core.Statement;
import HPalang.LTSGeneration.Labels.Reset;

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
//        ContinuousVariable delayVar = actorState.GetActor().GetDelayVariable();
//        ContinuousBehavior behavior = CreateDelayBehavior(actorState.GetActor(), statement.GetDelay(), delayVar);
////        actorState.SuspendedStatements().Enqueue(actorState.StatementQueue());
////        actorState.StatementQueue().Clear();
//        actorState.SetSuspended(true);
//        actorState.ContinuousBehaviors().Add(behavior);
    }
    
    @Override
    protected SoftwareLabel CreateTransitionLabel(ActorRunTimeState actorState, DelayStatement statement)
    {
        return new SoftwareLabel(Reset.ResetsFrom(new Reset(actorState.GetActor().GetDelayVariable(), new ConstantContinuousExpression(0f))));
    }
    
    private ContinuousBehavior CreateDelayBehavior(Actor actor,float delay, ContinuousVariable delayVar)
    {
        DefferentialEquation equation = new DefferentialEquation(delayVar, "1");
        return new ContinuousBehavior(delayVar+"<="+delay,equation,delayVar+"=="+delay,Statement.StatementsFrom(new ResumeStatement()));
    }
}
