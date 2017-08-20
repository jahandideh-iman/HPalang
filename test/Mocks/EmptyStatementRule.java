/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Mocks;

import HPalang.LTSGeneration.RunTimeStates.SoftwareActorState;
import HPalang.LTSGeneration.SOSRules.StatementRule;

/**
 *
 * @author Iman Jahandideh
 */
public class EmptyStatementRule extends StatementRule<EmptyStatement>
{

    @Override
    protected Class<EmptyStatement> StatementType()
    {
        return EmptyStatement.class;
    }

    @Override
    protected void ApplyStatement(SoftwareActorState actorState, EmptyStatement statement)
    {
        
    }
    
}
