/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.SOSRules;

import HPalang.Core.Statements.ContinuousAssignmentStatement;
import HPalang.LTSGeneration.LTSGenerator;
import HPalang.LTSGeneration.Reset;
import HPalang.LTSGeneration.RunTimeStates.ActorRunTimeState;
import HPalang.LTSGeneration.RunTimeStates.GlobalRunTimeState;
import HPalang.LTSGeneration.TauLabel;

/**
 *
 * @author Iman Jahandideh
 */
public class ContinuousAssignment extends StatementRule<ContinuousAssignmentStatement>
{
    @Override
    protected Class<ContinuousAssignmentStatement> StatementType()
    {
        return ContinuousAssignmentStatement.class;
    }

    @Override
    protected void ApplyStatement(ActorRunTimeState actorState, ContinuousAssignmentStatement statement)
    {
        
    }
    
    @Override
    protected TauLabel CreateTransitionLabel(ActorRunTimeState actorState, ContinuousAssignmentStatement statement)
    {
        return new TauLabel(new Reset(statement.Variable(), statement.Expression()));
    }
    
}
