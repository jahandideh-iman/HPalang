/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.SOSRules;

import HPalang.Core.Expression;
import HPalang.Core.Statement;
import HPalang.Core.Statements.AssignmentStatement;
import HPalang.Core.Variable;
import HPalang.LTSGeneration.Labels.Reset;
import HPalang.LTSGeneration.RunTimeStates.SoftwareActorState;
import Mocks.ComputableExpression;
import Mocks.EmptyExpression;
import Mocks.FakeVariable;
import Mocks.UncomputableExpression;
import Mocks.ValuationContainerMock;
import org.junit.Test;
import static org.junit.Assert.*;
import static TestUtilities.CoreUtility.*;
import static TestUtilities.NetworkingUtility.*;
import java.util.Set;
import static org.hamcrest.CoreMatchers.*;
import org.junit.Before;

/**
 *
 * @author Iman Jahandideh
 */
public class AssignmentStatementRuleTest extends SOSRuleTestFixture
{
    Variable variable = new FakeVariable("variable");
    ValuationContainerMock valuationMock = new ValuationContainerMock();
    SoftwareActorState actorState = CreateSoftwareActorState("actor");
    
    @Before
    public void Setup()
    {
        rule = new AssignmentStatementRule();
        
                
        AddActorState(actorState, globalState);
    }
    
    @Test 
    public void AddsTheValueToTheValuationIfTheExpressionIsEvaluatable()
    {
        Variable var = new FakeVariable("var");
        int value = 13;
        Expression  expr = new ComputableExpression(value);
        Statement assignment = new AssignmentStatement(var, expr);
        
        actorState.ValuationState().SetValuation(valuationMock);
        EnqueueStatement(assignment, actorState);
        
        rule.TryApply(SimpleStateInfo(globalState), transitionCollectorChecker);
        
        assertThat(valuationMock.ValueFor(var), equalTo(value));
    }
    
    @Test 
    public void AddsPartialExpressionToLabelIfTheExpressionIsEvaluatable()
    {
        Expression partialExpression = new EmptyExpression();
        Expression  expr = new UncomputableExpression(partialExpression);
        
        Statement assignment = new AssignmentStatement(variable, expr);
        EnqueueStatement(assignment, actorState);
        
        rule.TryApply(SimpleStateInfo(globalState), transitionCollectorChecker);
        
        Reset expectedReset = new Reset(variable, partialExpression);
        
        assertThat((Set<Reset>)CollectedLabel().GetResets(), hasItem(equalTo(expectedReset)));
    }
    
}