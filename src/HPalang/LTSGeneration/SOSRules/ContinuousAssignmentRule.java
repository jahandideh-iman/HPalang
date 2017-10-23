/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.SOSRules;

import HPalang.Core.Statements.ContinuousAssignmentStatement;
import HPalang.LTSGeneration.Labels.Reset;
import HPalang.LTSGeneration.RunTimeStates.SoftwareActorState;
import HPalang.LTSGeneration.Labels.SoftwareLabel;
import HPalang.LTSGeneration.RunTimeStates.PhysicalActorState;

/**
 *
 * @author Iman Jahandideh
 */
@Deprecated // Use AssignmentRule
public class ContinuousAssignmentRule extends PhysicalStatementRule<ContinuousAssignmentStatement>
{
    @Override
    protected Class<ContinuousAssignmentStatement> StatementType()
    {
        return ContinuousAssignmentStatement.class;
    }

    @Override
    protected void ApplyStatement(PhysicalActorState actorState, ContinuousAssignmentStatement statement)
    {
        
    }
    
    @Override
    protected SoftwareLabel CreateTransitionLabel(PhysicalActorState actorState, ContinuousAssignmentStatement statement)
    {
        return new SoftwareLabel(Reset.From(new Reset(statement.Variable(), statement.Expression())));
    }
    
}
