/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.SOSRules;

import org.junit.Test;
import org.junit.Before;


/**
 *
 * @author Iman Jahandideh
 */

public class DelayStatementRuleTest extends SOSRuleTestFixture
{ 
    @Before
    public void Setup()
    {
        ltsGenerator.AddSOSRule(new DelayStatementRule());
    }
    
    @Test
    public void ForEachActorStateIfNextStatementIsDelayThenDelaysTheActorStateAndAddsDelayBehavior()
    {
//        Actor actor1 = new ActorBuilder().WithID("actor1").WithCapacity(1).Build();
//       
//        ActorRunTimeStateBuilder actor1State = new ActorRunTimeStateBuilder()
//                .WithActor(actor1)
//                .EnqueueStatement(new DelayStatement(1.0f))
//                .EnqueueStatement(new EmptyStatement());
//        
//        globalState
//                .AddActorRunTimeState(actor1State);
//                
//                 
//        generatedLTS = ltsGenerator.Generate(globalState.Build());
//        
//        GlobalRunTimeState stateAfterActor1Delay = globalState.Build();
//        ActorRunTimeState stateAfterActor1Delay_Actor1 = stateAfterActor1Delay.FindActorState(actor1);
//        stateAfterActor1Delay_Actor1.FindSubState(ExecutionQueueState.class).Statements().Dequeue();
//        stateAfterActor1Delay_Actor1.SuspendedStatements().Enqueue(stateAfterActor1Delay_Actor1.StatementQueue());
//        stateAfterActor1Delay_Actor1.FindSubState(ExecutionQueueState.class).Statements().Clear();
//        stateAfterActor1Delay_Actor1.SetSuspended(true);
//        ContinuousVariable actor1DelayVar = actor1.GetDelayVariable();
//        stateAfterActor1Delay_Actor1.ContinuousBehaviors().Add(new ContinuousBehavior(
//                actor1DelayVar+"<="+1.0f 
//                , new DefferentialEquation(actor1DelayVar, "1")
//                , actor1DelayVar+"=="+1.0f
//                , StatementsFrom(new ResumeStatement())));
//        
//        SoftwareLabel label = new SoftwareLabel(Reset.ResetsFrom(new Reset(actor1.GetDelayVariable(), new ConstantContinuousExpression(0f))));
//
//        assertTrue(generatedLTS.HasTransition(globalState.Build(), label, stateAfterActor1Delay));
    }
    
}
