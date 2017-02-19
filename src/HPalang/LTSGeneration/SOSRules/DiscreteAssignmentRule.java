/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.SOSRules;

import HPalang.Core.Statements.DiscreteAssignmentStatement;
import HPalang.LTSGeneration.RunTimeStates.ActorRunTimeState;

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
    protected void ApplyStatement(ActorRunTimeState actorState, DiscreteAssignmentStatement statement)
    {
        actorState.Valuations().Set(statement.Variable(), statement.Expression().Evaluate(actorState.Valuations()));
    }
    
}
