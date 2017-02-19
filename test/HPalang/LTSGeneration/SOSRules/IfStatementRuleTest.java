/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.SOSRules;

import Builders.ActorBuilder;
import Builders.ActorRunTimeStateBuilder;
import HPalang.Core.Actor;
import HPalang.Core.DiscreteExpressions.ConstantDiscreteExpression;
import HPalang.Core.DiscreteVariable;
import HPalang.Core.Statement;
import HPalang.Core.Statements.DiscreteAssignmentStatement;
import HPalang.Core.Statements.IfStatement;
import HPalang.LTSGeneration.GlobalRunTimeState;
import HPalang.LTSGeneration.TauLabel;
import Mocks.EmptyStatement;
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
        Actor actor1 = new ActorBuilder()
                .WithID("actor1")
                .Build();
       
        IfStatement ifStatement = CreateTrueIfStatement();
        ActorRunTimeStateBuilder actor1State = new ActorRunTimeStateBuilder()
                .WithActor(actor1)
                .EnqueueStatement(ifStatement);
        
        globalState
                .AddActorRunTimeState(actor1State);
                
                 
        generatedLTS = ltsGenerator.Generate(globalState.Build());
        
        GlobalRunTimeState expectedState = globalState.Build();
        expectedState.FindActorState(actor1).StatementQueue().Dequeue();
        expectedState.FindActorState(actor1).StatementQueue().Push(ifStatement.TrueStatements());
       

        assertTrue(generatedLTS.HasTransition(globalState.Build(), new TauLabel(), expectedState));
    }
    
    @Test
    public void IfEvaluationIsFalseThenAddFalseStatementsToHeadOfStatementQueue()
    {
        Actor actor1 = new ActorBuilder()
                .WithID("actor1")
                .Build();
       
        IfStatement ifStatement = CreateFalseIfStatement();
        ActorRunTimeStateBuilder actor1State = new ActorRunTimeStateBuilder()
                .WithActor(actor1)
                .EnqueueStatement(ifStatement);
        
        globalState
                .AddActorRunTimeState(actor1State);
                
                 
        generatedLTS = ltsGenerator.Generate(globalState.Build());
        
        GlobalRunTimeState expectedState = globalState.Build();
        expectedState.FindActorState(actor1).StatementQueue().Dequeue();
        expectedState.FindActorState(actor1).StatementQueue().Push(ifStatement.FalseStatements());
       

        assertTrue(generatedLTS.HasTransition(globalState.Build(), new TauLabel(), expectedState));
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
