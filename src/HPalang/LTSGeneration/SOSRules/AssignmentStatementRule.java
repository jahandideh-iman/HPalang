/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.SOSRules;

import HPalang.Core.Expression;
import HPalang.LTSGeneration.ExpressionScopeUnwrapper;
import HPalang.Core.Statements.AssignmentStatement;
import HPalang.Core.ValuationContainer;
import HPalang.LTSGeneration.Labels.Reset;
import HPalang.LTSGeneration.Labels.SoftwareLabel;
import HPalang.LTSGeneration.RunTimeStates.ActorState;
import HPalang.LTSGeneration.RunTimeStates.GlobalRunTimeState;
import HPalang.LTSGeneration.RunTimeStates.ValuationState;
import static HPalang.LTSGeneration.SOSRules.Utilities.UnWrapExpressionScope;
import static HPalang.LTSGeneration.SOSRules.Utilities.UnWrapVariableScope;

/**
 *
 * @author Iman Jahandideh
 */
public class AssignmentStatementRule extends StatementRule<AssignmentStatement>
{

    @Override
    protected Class<AssignmentStatement> StatementType()
    {
        return AssignmentStatement.class;
    }

    @Override
    protected void ApplyStatement(ActorState newActorState, AssignmentStatement statement, GlobalRunTimeState newGlobalState)
    {
        if(statement.Expression().IsComputable(newActorState.ValuationState().Valuation()))
        {
            ValuationContainer valuation = FindValuation(newActorState);
            
            valuation.Set(statement.Variable(), statement.Expression().Evaluate(valuation));
        }
    }

    @Override
    protected SoftwareLabel CreateTransitionLabel(ActorState actorState, AssignmentStatement statement)
    {
        ValuationContainer valuation = FindValuation(actorState);
        
        if(statement.Expression().IsComputable(actorState.ValuationState().Valuation()) == false)
        {

            return new SoftwareLabel(Reset.From(
                    new Reset(
                            UnWrapVariableScope(statement.Variable(), actorState.Actor()),
                            UnWrapExpressionScope( statement.Expression(), actorState.Actor(), valuation)
                    )));
        }
       
        return new SoftwareLabel();
    }
    
    

    private ValuationContainer FindValuation(ActorState actorState)
    {
        return actorState.ValuationState().Valuation();
    }
    
}
