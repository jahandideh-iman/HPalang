/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.SOSRules;

import HPalang.Core.Statements.MessageTeardownStatement;
import HPalang.Core.VariableParameter;
import HPalang.LTSGeneration.RunTimeStates.SoftwareActorState;

/**
 *
 * @author Iman Jahandideh
 */
public class MessageTeardownStatementRule extends StatementRule<MessageTeardownStatement>
{

    @Override
    protected Class<MessageTeardownStatement> StatementType()
    {
        return MessageTeardownStatement.class;
    }

    @Override
    protected void ApplyStatement(SoftwareActorState actorState, MessageTeardownStatement statement)
    {
        for(VariableParameter parameter : statement.ParametersToRemove().AsSet())
            actorState.ValuationState().Valuation().Remove(parameter.Variable());
        
    }
    
}
