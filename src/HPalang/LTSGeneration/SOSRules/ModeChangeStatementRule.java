/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.SOSRules;

import HPalang.Core.Statements.ModeChangeStatement;
import HPalang.LTSGeneration.RunTimeStates.ActorState;
import HPalang.LTSGeneration.RunTimeStates.PhysicalActorState;

/**
 *
 * @author Iman Jahandideh
 */
public class ModeChangeStatementRule extends PhysicalStatementRule<ModeChangeStatement>
{

    @Override
    protected Class<ModeChangeStatement> StatementType()
    {
        return ModeChangeStatement.class;
    }

    @Override
    protected void ApplyStatement(PhysicalActorState newActorState, ModeChangeStatement statement)
    {
        newActorState.SetMode(statement.Mode());
    }
    
}
