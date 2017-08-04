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
import HPalang.Core.Statements.DiscreteAssignmentStatement;
import HPalang.LTSGeneration.RunTimeStates.ExecutionQueueState;
import HPalang.LTSGeneration.RunTimeStates.GlobalRunTimeState;
import HPalang.LTSGeneration.RunTimeStates.ValuationState;
import HPalang.LTSGeneration.Labels.SoftwareLabel;
import static org.hamcrest.CoreMatchers.is;
import org.hamcrest.core.IsEqual;
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

    @Test
    public void ForEachActorStateIfNextStatementIsDiscreteAssignementThenAssignsTheNewValue()
    {
        DiscreteVariable dVar = new DiscreteVariable("dVar");
        Actor actor1 = new ActorBuilder()
                .WithID("actor1")
                .WithDiscreteVariable(dVar)
                .Build();
       
        ActorRunTimeStateBuilder actor1State = new ActorRunTimeStateBuilder()
                .WithActor(actor1)
                .EnqueueStatement(new DiscreteAssignmentStatement(dVar, new ConstantDiscreteExpression(5)));
        
        globalState.AddActorRunTimeState(actor1State.Build());
                
                 
        generatedLTS = ltsGenerator.Generate(globalState);
        
        GlobalRunTimeState expectedState = globalState.DeepCopy();
        expectedState.FindActorState(actor1).FindSubState(ExecutionQueueState.class).Statements().Dequeue();
        expectedState.FindActorState(actor1).FindSubState(ValuationState.class).Valuation().Set(dVar, 5);
       

        assertTrue(generatedLTS.HasTransition(globalState, new SoftwareLabel(), expectedState));
        assertThat(generatedLTS.GetStates().size(), is(IsEqual.equalTo(2)));
    }
}
