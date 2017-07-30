/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.SOSRules.TierOne;

import Builders.ActorBuilder;
import Builders.ActorRunTimeStateBuilder;
import HPalang.Core.Actor;
import HPalang.Core.DefferentialEquation;
import HPalang.LTSGeneration.GuardedlLabel;
import HPalang.Core.Messages.MessageWithBody;
import HPalang.LTSGeneration.RunTimeStates.ActorRunTimeState;
import HPalang.LTSGeneration.RunTimeStates.ContinuousBehavior;
import HPalang.LTSGeneration.RunTimeStates.GlobalRunTimeState;
import HPalang.LTSGeneration.SOSRules.SOSRuleTestFixture;
import static HPalang.Core.Statement.StatementsFrom;
import Mocks.EmptyStatement;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 *
 * @author Iman Jahandideh
 */
public class ContinuousBehaviorExpirationRuleTest extends SOSRuleTestFixture
{    
    @Before
    public void Setup()
    {
        ltsGenerator.AddSOSRule(new ContinuousBehaviorExpirationRule());
    }
    
    @Test
    public void ForEachContinousBehaviorAddsDeprication()
    {
//        Actor actor1 = new ActorBuilder().WithID("actor1").WithCapacity(1).Build();
//       
//        ActorRunTimeStateBuilder actor1State = new ActorRunTimeStateBuilder()
//                .WithActor(actor1)
//                .AddBehavior(new ContinuousBehavior("inv1",DefferentialEquation.Empty("eq1"),"guard1",StatementsFrom(new EmptyStatement())))
//                .AddBehavior(new ContinuousBehavior("inv2",DefferentialEquation.Empty("eq2"),"guard2",StatementsFrom(new EmptyStatement())));
//        
//        
//        globalState
//                .AddActorRunTimeState(actor1State);
//                
//                 
//        generatedLTS = ltsGenerator.Generate(globalState.Build());
//        
//        GlobalRunTimeState nextGlobalState1 = globalState.Build();
//        ActorRunTimeState nextActorState1 = nextGlobalState1.FindActorState(actor1);
//        nextActorState1.ContinuousBehaviors().Remove(new ContinuousBehavior("inv1",DefferentialEquation.Empty("eq1"),"guard1",StatementsFrom(new EmptyStatement())));
//        //nextActorState1.HighPriorityMessageQueue().Enqueue(new MessageWithBody(StatementsFrom(new EmptyStatement())));
//        
//        GlobalRunTimeState nextGlobalState2 = globalState.Build();
//        ActorRunTimeState nextActorState2 = nextGlobalState2.FindActorState(actor1);
//        nextActorState2.ContinuousBehaviors().Remove(new ContinuousBehavior("inv2",DefferentialEquation.Empty("eq2"),"guard2",StatementsFrom(new EmptyStatement())));
//        //nextActorState2.HighPriorityMessageQueue().Enqueue(new MessageWithBody(StatementsFrom(new EmptyStatement())));
//
//        assertTrue(generatedLTS.HasTransition(globalState.Build(), new GuardedlLabel("guard1"), nextGlobalState1));
//        assertTrue(generatedLTS.HasTransition(globalState.Build(), new GuardedlLabel("guard2"), nextGlobalState2));
    }
}
