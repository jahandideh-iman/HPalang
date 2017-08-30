/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.SOSRules;

import HPalang.Core.Statements.DiscreteAssignmentStatement;
import HPalang.Core.SimpleValuationContainer;
import HPalang.Core.Variables.IntegerVariable;
import HPalang.LTSGeneration.RunTimeStates.SoftwareActorState;
import HPalang.LTSGeneration.RunTimeStates.ValuationState;

/**
 *
 * @author Iman Jahandideh
 */
public class DiscreteAssignmentRule extends StatementRule<DiscreteAssignmentStatement>
{

    @Override
    protected Class<DiscreteAssignmentStatement> StatementType()
    {
        return DiscreteAssignmentStatement.class;
    }

    @Override
    protected void ApplyStatement(SoftwareActorState actorState, DiscreteAssignmentStatement statement)
    {
        actorState.FindSubState(ValuationState.class).Valuation().
                Set((IntegerVariable )statement.Variable(), statement.Expression().Evaluate(actorState.ValuationState().Valuation()));
    }
    
}
