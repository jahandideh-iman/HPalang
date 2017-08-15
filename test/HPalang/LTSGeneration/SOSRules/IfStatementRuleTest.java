/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.SOSRules;

import Builders.ActorBuilder;
import Builders.ActorRunTimeStateBuilder;
import HPalang.Core.SoftwareActor;
import HPalang.Core.DiscreteExpressions.ConstantDiscreteExpression;
import HPalang.Core.Statement;
import HPalang.Core.Statements.IfStatement;
import HPalang.LTSGeneration.RunTimeStates.ExecutionQueueState;
import HPalang.LTSGeneration.RunTimeStates.GlobalRunTimeState;
import HPalang.LTSGeneration.Labels.SoftwareLabel;
import Mocks.EmptyStatement;
import static org.hamcrest.CoreMatchers.is;
import org.hamcrest.core.IsEqual;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 *
 * @author Iman Jahandideh
 */
public class IfStatementRuleTest extends SOSRuleTestFixture
{
    @Before
    public void Setup()
    {
        ltsGenerator.AddSOSRule(new IfStatementRule());
    }
    
    @Test
    public void IfEvaluationIsTrueThenAddTrueStatementsToHeadOfStatementQueue()
    {
        SoftwareActor actor1 = new ActorBuilder()
                .WithID("actor1")
                .Build();
       
        IfStatement ifStatement = CreateTrueIfStatement();
        ActorRunTimeStateBuilder actor1State = new ActorRunTimeStateBuilder()
                .WithActor(actor1)
                .EnqueueStatement(ifStatement);
        
        globalState
                .AddActorRunTimeState(actor1State.Build());
                
                 
        generatedLTS = ltsGenerator.Generate(globalState);
        
        GlobalRunTimeState expectedState = globalState.DeepCopy();
        expectedState.FindActorState(actor1).FindSubState(ExecutionQueueState.class).Statements().Dequeue();
        expectedState.FindActorState(actor1).FindSubState(ExecutionQueueState.class).Statements().Push(ifStatement.TrueStatements());
       

        assertTrue(generatedLTS.HasTransition(globalState, new SoftwareLabel(), expectedState));
        assertThat(generatedLTS.GetStates().size(), is(IsEqual.equalTo(2)));
    }
    
    @Test
    public void IfEvaluationIsFalseThenAddFalseStatementsToHeadOfStatementQueue()
    {
        SoftwareActor actor1 = new ActorBuilder()
                .WithID("actor1")
                .Build();
       
        IfStatement ifStatement = CreateFalseIfStatement();
        ActorRunTimeStateBuilder actor1State = new ActorRunTimeStateBuilder()
                .WithActor(actor1)
                .EnqueueStatement(ifStatement);
        
        globalState
                .AddActorRunTimeState(actor1State.Build());
                
                 
        generatedLTS = ltsGenerator.Generate(globalState);
        
        GlobalRunTimeState expectedState = globalState.DeepCopy();
        expectedState.FindActorState(actor1).FindSubState(ExecutionQueueState.class).Statements().Dequeue();
        expectedState.FindActorState(actor1).FindSubState(ExecutionQueueState.class).Statements().Push(ifStatement.FalseStatements());
       

        assertTrue(generatedLTS.HasTransition(globalState, new SoftwareLabel(), expectedState));
        assertThat(generatedLTS.GetStates().size(), is(IsEqual.equalTo(2)));
    }
    
    private IfStatement CreateTrueIfStatement()
    {
        return new IfStatement(new ConstantDiscreteExpression(1)
                , Statement.StatementsFrom(new EmptyStatement("true"))
                , Statement.StatementsFrom(new EmptyStatement("false")));
    }
    
    private IfStatement CreateFalseIfStatement()
    {
        return new IfStatement(new ConstantDiscreteExpression(0)
                , Statement.StatementsFrom(new EmptyStatement("true"))
                , Statement.StatementsFrom(new EmptyStatement("false")));
    }
}
