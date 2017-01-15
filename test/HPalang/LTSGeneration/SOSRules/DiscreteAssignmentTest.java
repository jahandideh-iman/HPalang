/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.SOSRules;

import Builders.ActorBuilder;
import Builders.ActorRunTimeStateBuilder;
import HPalang.Core.Actor;
import HPalang.Core.ConstantDiscreteExpression;
import HPalang.Core.DiscreteVariable;
import HPalang.Core.Statements.DiscreteAssignmentStatement;
import HPalang.LTSGeneration.RunTimeStates.GlobalRunTimeState;
import HPalang.LTSGeneration.TauLabel;
import org.junit.*;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 *
 * @author Iman Jahandideh
 */
public class DiscreteAssignmentTest extends SOSRuleTestFixture
{
    
    @Before
    public void Setup()
    {
        ltsGenerator.AddSOSRule(new DiscreteAssignmentRule());
    }

    @Ignore
    @Test
    public void testSomeMethod()
    {
        DiscreteVariable dVar = new DiscreteVariable("dVar");
        Actor actor1 = new ActorBuilder()
                .WithID("actor1")
                .WithDiscreteVariable(dVar)
                .Build();
       
        ActorRunTimeStateBuilder actor1State = new ActorRunTimeStateBuilder()
                .WithActor(actor1)
                .EnqueueStatement(new DiscreteAssignmentStatement(dVar, new ConstantDiscreteExpression(5)));
        
        globalState
                .AddActorRunTimeState(actor1State);
                
                 
        generatedLTS = ltsGenerator.Generate(globalState.Build());
        
        GlobalRunTimeState expectedState = globalState.Build();
        expectedState.FindActorState(actor1).StatementQueue().Dequeue();
        expectedState.FindActorState(actor1).SetDiscreteValue(dVar, 5);
       

        assertTrue(generatedLTS.HasTransition(globalState.Build(), new TauLabel(), expectedState));
    }
    
}
