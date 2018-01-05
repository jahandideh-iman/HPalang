/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.SOSRules;

import Builders.SoftwareActorBuilder;
import HPalang.LTSGeneration.Builders.SoftwareActorStateBuilder;
import HPalang.Core.DiscreteExpressions.BinaryExpression;
import HPalang.Core.DiscreteExpressions.BinaryOperators.EqualityOperator;
import HPalang.Core.SoftwareActor;
import HPalang.Core.DiscreteExpressions.ConstantDiscreteExpression;
import HPalang.Core.DiscreteExpressions.FalseConst;
import HPalang.Core.DiscreteExpressions.TrueConst;
import HPalang.Core.Statement;
import HPalang.Core.Statements.IfStatement;
import HPalang.LTSGeneration.Labels.Guard;
import HPalang.LTSGeneration.Labels.Reset;
import HPalang.LTSGeneration.RunTimeStates.ExecutionQueueState;
import HPalang.LTSGeneration.RunTimeStates.GlobalRunTimeState;
import HPalang.LTSGeneration.Labels.SoftwareLabel;
import HPalang.LTSGeneration.RunTimeStates.SoftwareActorState;
import static HPalang.LTSGeneration.SOSRules.Utilities.PartivalValuation;
import Mocks.EmptyExpression;
import Mocks.EmptyStatement;
import Mocks.UncomputableExpression;
import static org.hamcrest.CoreMatchers.is;
import org.hamcrest.core.IsEqual;
import org.junit.Test;
import static org.junit.Assert.*;
import static TestUtilities.CoreUtility.*;
import static org.hamcrest.CoreMatchers.equalTo;
import org.junit.Before;

/**
 *
 * @author Iman Jahandideh
 */
public class IfStatementRuleTest extends SOSRuleTestFixture
{
    
    SoftwareActorState actorState = CreateSoftwareActorState("actor");
    SoftwareActor actor;
    
    @Before
    public void Setup()
    {
        rule = new IfStatementRule();
        actor = actorState.SActor();
        
        AddActorState(actorState, globalState);
    }
    
    @Test
    public void IfConditionIsTrueThenAddTrueStatementsToHeadOfStatementQueue()
    {
        IfStatement ifStatement = CreateTrueIfStatement();
        EnqueueStatement(ifStatement, actorState); 
        
        ApplyRuleOn(globalState);
        //rule.TryApply(SimpleStateInfo(globalState), transitionCollectorChecker);
        
        GlobalRunTimeState expectedGlobalState = globalState.DeepCopy();
        DequeueOneStatemenet(actor, expectedGlobalState);
        EnqueueStatements(ifStatement.TrueStatements(), actor, expectedGlobalState);
        
        assertThat(FindActorState(actor, CollectedGlobalState()).ExecutionQueueState()
                , equalTo(FindActorState(actor, expectedGlobalState).ExecutionQueueState()));
        
        transitionCollectorChecker.ExpectTransition(new SoftwareLabel(), expectedGlobalState);
        VerifyEqualOutputForMultipleApply(SimpleStateInfo(globalState));
    }
    @Test
    public void IsNotAppliedIfActorIsSuspended()
    {
        IfStatement ifStatement = CreateTrueIfStatement();
        actorState.SetSuspended(true);
        EnqueueStatement(ifStatement, actorState); 
        
        ApplyRuleOn(globalState);
        
        transitionCollectorChecker.ExpectNoTransition();
        VerifyEqualOutputForMultipleApply(SimpleStateInfo(globalState));
    }
    @Test
    public void IfConditionIsFalseThenAddFalseStatementsToHeadOfStatementQueue()
    {
        IfStatement ifStatement = CreateFalseIfStatement();
        EnqueueStatement(ifStatement, actorState); 
        
        ApplyRuleOn(globalState);
        //rule.TryApply(SimpleStateInfo(globalState), transitionCollectorChecker);
        
        GlobalRunTimeState expectedGlobalState = globalState.DeepCopy();
        DequeueOneStatemenet(actor, expectedGlobalState);
        EnqueueStatements(ifStatement.FalseStatements(), actor, expectedGlobalState);
        
        transitionCollectorChecker.ExpectTransition(new SoftwareLabel(), expectedGlobalState);
        VerifyEqualOutputForMultipleApply(SimpleStateInfo(globalState));
    }
    
    @Test
    public void IfConditionIsNotEvaluatableAddsBothPossibleTransitions()
    {
        IfStatement ifStatement = CreateNotEvalutableIfStatement();
        EnqueueStatement(ifStatement, actorState); 
        
        transitionCollectorChecker.SetAllowedTransitions(2);
        
        ApplyRuleOn(globalState);
        //rule.TryApply(SimpleStateInfo(globalState), transitionCollectorChecker);
        
        GlobalRunTimeState falsePathGlobalState = globalState.DeepCopy();
        DequeueOneStatemenet(actor, falsePathGlobalState);
        EnqueueStatements(ifStatement.FalseStatements(), actor, falsePathGlobalState);
        
        GlobalRunTimeState truePathGlobalState = globalState.DeepCopy();
        DequeueOneStatemenet(actor, truePathGlobalState);
        EnqueueStatements(ifStatement.TrueStatements(), actor, truePathGlobalState);
        
        
        Guard falsePathGuard = new Guard(new BinaryExpression(
                PartivalValuation(ifStatement.Expression(), actor, falsePathGlobalState), 
                new EqualityOperator(), 
                new FalseConst()));
        
        Guard truePathGuard = new Guard(new BinaryExpression(
                PartivalValuation(ifStatement.Expression(), actor, truePathGlobalState),
                new EqualityOperator(),
                new TrueConst()));
        
        
        transitionCollectorChecker.ExpectTransition(new SoftwareLabel(truePathGuard), truePathGlobalState);
        transitionCollectorChecker.ExpectTransition(new SoftwareLabel(falsePathGuard), falsePathGlobalState);
        VerifyEqualOutputForMultipleApply(SimpleStateInfo(globalState));
    }
    
    private IfStatement CreateTrueIfStatement()
    {
        return new IfStatement(new TrueConst()
                , Statement.StatementsFrom(new EmptyStatement("true"))
                , Statement.StatementsFrom(new EmptyStatement("false")));
    }
    
    private IfStatement CreateFalseIfStatement()
    {
        return new IfStatement(new FalseConst()
                , Statement.StatementsFrom(new EmptyStatement("true"))
                , Statement.StatementsFrom(new EmptyStatement("false")));
    }
    
    private IfStatement CreateNotEvalutableIfStatement()
    {
        return new IfStatement(new UncomputableExpression(new EmptyExpression())
                , Statement.StatementsFrom(new EmptyStatement("true"))
                , Statement.StatementsFrom(new EmptyStatement("false")));
    }
}
