/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.SOSRules;

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
    protected void ApplyStatement(ActorState actorState, AssignmentStatement statement)
    {
        if(statement.Expression().IsComputable(null))
        {
            ValuationContainer valuation = FindValuation(actorState);
            
            valuation.Set(statement.Variable(), statement.Expression().Evaluate(valuation));
        }
    }

    @Override
    protected SoftwareLabel CreateTransitionLabel(ActorState actorState, AssignmentStatement statement)
    {
        ValuationContainer valuation = FindValuation(actorState);
        
        if(statement.Expression().IsComputable(null) == false)
        {
            
            return new SoftwareLabel(Reset.From(
                    new Reset(
                            statement.Variable(),
                            statement.Expression().PartiallyEvaluate(valuation)
                    )));
        }
       
        return new SoftwareLabel();
    }
    
    

    private ValuationContainer FindValuation(ActorState actorState)
    {
        ValuationState valuationState = (ValuationState) actorState.FindSubState(ValuationState.class);
        
        if(valuationState == null)
            return null;
        
        return valuationState.Valuation();
    }
    
}
