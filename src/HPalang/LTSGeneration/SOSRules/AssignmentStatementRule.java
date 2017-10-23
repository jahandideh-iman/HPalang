/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.SOSRules;

import HPalang.Core.Expression;
import HPalang.Core.ExpressionScopeUnwrapper;
import HPalang.Core.Statements.AssignmentStatement;
import HPalang.Core.ValuationContainer;
import HPalang.LTSGeneration.Labels.Reset;
import HPalang.LTSGeneration.Labels.SoftwareLabel;
import HPalang.LTSGeneration.RunTimeStates.ActorState;
import HPalang.LTSGeneration.RunTimeStates.ValuationState;

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
    protected void ApplyStatement(ActorState newActorState, AssignmentStatement statement)
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
            
            ExpressionScopeUnwrapper scopeUnwrapper = new ExpressionScopeUnwrapper();
            String actorName = actorState.Actor().Name();
            
            return new SoftwareLabel(Reset.From(
                    new Reset(
                            scopeUnwrapper.Unwrap(statement.Variable(), actorName),
                            scopeUnwrapper.Unwrap(
                                    statement.Expression().PartiallyEvaluate(valuation),
                                    actorName)
                    )));
        }
       
        return new SoftwareLabel();
    }
    
    

    private ValuationContainer FindValuation(ActorState actorState)
    {
        return actorState.ValuationState().Valuation();
    }
    
}
