/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.SOSRules;

import HPalang.Core.DiscreteExpressions.VariableExpression;
import HPalang.Core.Statements.MessageTeardownStatement;
import HPalang.Core.VariableArgument;
import HPalang.Core.VariableParameter;
import HPalang.Core.Variables.RealVariable;
import HPalang.LTSGeneration.RunTimeStates.GlobalRunTimeState;
import HPalang.LTSGeneration.RunTimeStates.SoftwareActorState;

/**
 *
 * @author Iman Jahandideh
 */
public class MessageTeardownStatementRule extends SoftwareStatementRule<MessageTeardownStatement>
{

    @Override
    protected Class<MessageTeardownStatement> StatementType()
    {
        return MessageTeardownStatement.class;
    }

    @Override
    protected void ApplyStatement(SoftwareActorState newActorState, MessageTeardownStatement statement, GlobalRunTimeState newGlobalState)
    {
        for(VariableParameter parameter : statement.ParametersToRemove().AsSet())
            newActorState.ValuationState().Valuation().Remove(parameter.Variable());
        
        for(RealVariable variable : statement.VariablesToRelease())
            newGlobalState.VariablePoolState().Pool().Release(variable);
        
    }
    
}
