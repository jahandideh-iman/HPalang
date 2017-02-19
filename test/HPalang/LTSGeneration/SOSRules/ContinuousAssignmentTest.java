/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.SOSRules;

import Builders.ActorBuilder;
import Builders.ActorRunTimeStateBuilder;
import HPalang.Core.Actor;
import HPalang.Core.ContinuousExpressions.ConstantExpression;
import HPalang.Core.ContinuousVariable;
import HPalang.Core.DiscreteVariable;
import HPalang.Core.DiscreteExpressions.ConstantDiscreteExpression;
import HPalang.Core.Statements.ContinuousAssignmentStatement;
import HPalang.Core.Statements.DiscreteAssignmentStatement;
import HPalang.LTSGeneration.GuardedlLabel;
import HPalang.LTSGeneration.Reset;
import HPalang.LTSGeneration.RunTimeStates.GlobalRunTimeState;
import HPalang.LTSGeneration.TauLabel;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 *
 * @author Iman Jahandideh
 */
public class ContinuousAssignmentTest extends SOSRuleTestFixture
{
    
    @Before
    public void Setup()
    {
        ltsGenerator.AddSOSRule(new ContinuousAssignment());
    }

    @Test
    public void ForEachActorStateIfNextStatementIsDiscreteAssignementThenAssignsTheNewValue()
    {
        ContinuousVariable cVar = new ContinuousVariable("cVar");
        Actor actor = new ActorBuilder()
                .WithID("actor")
                .Build();
       
        ActorRunTimeStateBuilder actorState = new ActorRunTimeStateBuilder()
                .WithActor(actor)
                .EnqueueStatement(new ContinuousAssignmentStatement(cVar, new ConstantExpression(1.5f)));
        
        globalState
                .AddActorRunTimeState(actorState);
                
                 
        generatedLTS = ltsGenerator.Generate(globalState.Build());
        
        GlobalRunTimeState expectedState = globalState.Build();
        expectedState.FindActorState(actor).StatementQueue().Dequeue();
       
        TauLabel label = new TauLabel(new Reset(cVar, new ConstantExpression(1.5f)));
        assertTrue(generatedLTS.HasTransition(globalState.Build(), label , expectedState));
    }
}
