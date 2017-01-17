/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.SOSRules.TierOne;

import Builders.ActorBuilder;
import Builders.ActorRunTimeStateBuilder;
import HPalang.Core.Actor;
import HPalang.Core.Statements.ResumeStatement;
import HPalang.LTSGeneration.RunTimeStates.GlobalRunTimeState;
import HPalang.LTSGeneration.SOSRules.SOSRuleTestFixture;
import HPalang.LTSGeneration.TauLabel;
import Mocks.EmptyStatement;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 *
 * @author Iman Jahandideh
 */
public class ResumeStatementRuleTest extends SOSRuleTestFixture
{
    
    @Before
    public void Setup()
    {
        ltsGenerator.AddSOSRule(new ResumeStatementRule());
    }

    @Test
    public void UnsuspendActorAndEnqueuesSuspendedStatements()
    {
        Actor actor1 = new ActorBuilder()
                .WithID("actor1")
                .Build();
       

        ActorRunTimeStateBuilder actor1State = new ActorRunTimeStateBuilder()
                .WithActor(actor1)
                .SetSuspended(true)
                .AddSuspendedStatement(new EmptyStatement("statement1"))
                .AddSuspendedStatement(new EmptyStatement("statement2"))
                .EnqueueStatement(new ResumeStatement());
        
        globalState
                .AddActorRunTimeState(actor1State);
                
                 
        generatedLTS = ltsGenerator.Generate(globalState.Build());
        
        GlobalRunTimeState expectedState = globalState.Build();
        expectedState.FindActorState(actor1).StatementQueue().Dequeue();
        expectedState.FindActorState(actor1).SuspendedStatements().Clear();
        expectedState.FindActorState(actor1).SetSuspended(false);
        expectedState.FindActorState(actor1).StatementQueue().Enqueue(new EmptyStatement("statement1"));
        expectedState.FindActorState(actor1).StatementQueue().Enqueue(new EmptyStatement("statement2"));

        assertTrue(generatedLTS.HasTransition(globalState.Build(), new TauLabel(), expectedState));
    }
    
}
