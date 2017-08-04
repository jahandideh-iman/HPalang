/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.SOSRules;

import HPalang.Core.ContinuousExpressions.ConstantContinuousExpression;
import HPalang.Core.ContinuousVariable;
import HPalang.Core.PhysicalActor;
import HPalang.Core.Statement;
import HPalang.Core.Statements.ContinuousAssignmentStatement;
import HPalang.LTSGeneration.Labels.Reset;
import HPalang.LTSGeneration.RunTimeStates.ContinuousState;
import HPalang.LTSGeneration.RunTimeStates.ExecutionQueueState;
import HPalang.LTSGeneration.RunTimeStates.GlobalRunTimeState;
import HPalang.LTSGeneration.RunTimeStates.PhysicalActorState;
import HPalang.LTSGeneration.Labels.SoftwareLabel;
import HPalang.LTSGeneration.State;
import static org.hamcrest.CoreMatchers.*;
import org.hamcrest.core.IsEqual;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 *
 * @author Iman Jahandideh
 */
public class ContinuousAssignmentRuleTest extends SOSRuleTestFixture
{
    
    @Before
    public void Setup()
    {
        ltsGenerator.AddSOSRule(new ContinuousAssignmentRule());
    }

    @Test
    public void SetsTheContinuousAssignmentLabel()
    {
        ContinuousVariable cVar = new ContinuousVariable("cVar");
        ContinuousAssignmentStatement assignment = new ContinuousAssignmentStatement(cVar, new ConstantContinuousExpression(1.5f));
        
        PhysicalActorState pActorState = CreatePhysicalState("pActor", new ExecutionQueueState());
        pActorState.ExecutionQueueState().Statements().Enqueue(assignment);

        ContinuousState continuousState = CreateContinuousState(pActorState);
        
        GlobalRunTimeState globalState = new GlobalRunTimeState();
        globalState.AddSubstate(continuousState);
                 
        generatedLTS = ltsGenerator.Generate(globalState);
        
        GlobalRunTimeState expectedState = globalState.DeepCopy();
        PhysicalActorState expectedPhysicalState = expectedState.ContinuousState().FindActorState(pActorState.Actor());
        expectedPhysicalState.ExecutionQueueState().Statements().Clear();

        SoftwareLabel label = new SoftwareLabel(Reset.ResetsFrom(new Reset(assignment.Variable(), assignment.Expression())));
        
        assertTrue(generatedLTS.HasTransition(globalState, label , expectedState));
        assertThat(generatedLTS.GetStates().size(), is(IsEqual.equalTo(2)));
    }
    
    private PhysicalActorState CreatePhysicalState(String actorName, State substate)
    {
        PhysicalActorState state =  new PhysicalActorState(new PhysicalActor(actorName));
        
        state.AddSubstate(substate);
        
        return state;
    }
    
    private ContinuousState CreateContinuousState(PhysicalActorState actorState)
    {
        ContinuousState continuousState = new ContinuousState();
        
        continuousState.AddPhysicalActorState(actorState);
        
        return continuousState;
    }
}
