/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.SOSRules;

import HPalang.Core.MessageParameters;
import HPalang.Core.Statement;
import HPalang.Core.Statements.MessageTeardownStatement;
import HPalang.Core.VariableParameter;
import HPalang.LTSGeneration.RunTimeStates.SoftwareActorState;
import Mocks.ValuationContainerMock;
import org.junit.Test;
import static org.junit.Assert.*;
import static TestUtilities.CoreUtility.*;
import static TestUtilities.NetworkingUtility.*;
import java.util.Collections;
import static org.hamcrest.CoreMatchers.*;
import org.junit.Before;

/**
 *
 * @author Iman Jahandideh
 */
public class MessageTeardownStatementRuleTest extends SOSRuleTestFixture
{
    @Before
    public void Setup()
    {
        rule = new MessageTeardownStatementRule();
    }

    @Test
    public void RemovesValuationForTheParametersInTheStatementFromTheActor()
    {
        ValuationContainerMock valuationMock = new ValuationContainerMock();
        
        SoftwareActorState actorState = CreateSoftwareActorState("actor");
        actorState.ValuationState().SetValuation(valuationMock);
        
        AddActorState(actorState, globalState);
        
        
        VariableParameter parameter = ParameterFor("param");
        MessageParameters messageParamters  = MessageParameters.From(parameter);
        
        Statement teardownStatemeent = new MessageTeardownStatement(messageParamters, Collections.EMPTY_LIST);
        EnqueueStatement(teardownStatemeent, actorState);
        
        ApplyAndVerifyRuleOn(globalState);
        //rule.TryApply(SimpleStateInfo(globalState), transitionCollectorChecker);
        
        assertThat(valuationMock.removedVariables, hasItem(parameter.Variable()));
        VerifyEqualOutputForMultipleApply(SimpleStateInfo(globalState));
    }
    
}
