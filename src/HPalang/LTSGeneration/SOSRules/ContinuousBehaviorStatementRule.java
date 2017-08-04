/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.SOSRules;

import HPalang.LTSGeneration.LTSGenerator;
import HPalang.Core.Message;
import HPalang.LTSGeneration.RunTimeStates.ActorRunTimeState;
import HPalang.LTSGeneration.RunTimeStates.GlobalRunTimeState;
import HPalang.LTSGeneration.Labels.SoftwareLabel;
import HPalang.Core.Statements.ContinuousBehaviorStatement;

/**
 *
 * @author Iman Jahandideh
 */
public class ContinuousBehaviorStatementRule extends StatementRule<ContinuousBehaviorStatement>
{

    @Override
    protected Class<ContinuousBehaviorStatement> StatementType()
    {
        return ContinuousBehaviorStatement.class;
    }

    @Override
    protected void ApplyStatement(ActorRunTimeState actorState, ContinuousBehaviorStatement statement)
    {           
        //actorState.ContinuousBehaviors().Add(statement.GetBehavior());
    }
    
}
