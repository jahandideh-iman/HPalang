/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.SOSRules;

import HPalang.Core.Statements.IfStatement;
import HPalang.LTSGeneration.RunTimeStates.ActorRunTimeState;
import HPalang.LTSGeneration.RunTimeStates.ExecutionQueueState;
import HPalang.LTSGeneration.RunTimeStates.ValuationState;

/**
 *
 * @author Iman Jahandideh
 */
public class IfStatementRule extends StatementRule<IfStatement>
{

    @Override
    protected Class<IfStatement> StatementType()
    {
        return IfStatement.class;
    }

    @Override
    protected void ApplyStatement(ActorRunTimeState actorState, IfStatement statement)
    {
        if(statement.Expression().Evaluate(actorState.FindSubState(ValuationState.class).Valuation()) > 0)
            actorState.FindSubState(ExecutionQueueState.class).Statements().Push(statement.TrueStatements());
        else
            actorState.FindSubState(ExecutionQueueState.class).Statements().Push(statement.FalseStatements());
    }
    
}
