/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.SOSRules.TierOne;

import Builders.ActorBuilder;
import Builders.ActorRunTimeStateBuilder;
import HPalang.Core.Actor;
import HPalang.Core.ContinuousExpressions.ConstantExpression;
import HPalang.Core.ContinuousVariable;
import HPalang.Core.Message;
import HPalang.Core.Messages.MessageWithBody;
import HPalang.Core.Messages.NormalMessage;
import static HPalang.Core.Statement.StatementsFrom;
import HPalang.Core.Statements.ContinuousAssignmentStatement;
import HPalang.LTSGeneration.GlobalRunTimeState;
import HPalang.LTSGeneration.GuardedlLabel;
import HPalang.LTSGeneration.LTSGenerator;
import HPalang.LTSGeneration.Reset;
import HPalang.LTSGeneration.RunTimeStates.ActorRunTimeState;
import HPalang.LTSGeneration.RunTimeStates.ContinuousBehavior;
import HPalang.LTSGeneration.SOSRules.ContinuousAssignmentRule;
import HPalang.LTSGeneration.SOSRules.SOSRuleTestFixture;
import HPalang.LTSGeneration.SOSRules.StatementRule;
import HPalang.LTSGeneration.TauLabel;
import Mocks.EmptyStatement;
import Mocks.EmptyStatementRule;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 *
 * @author Iman Jahandideh
 */
public class LowPriorityMessageTakeRuleTest extends SOSRuleTestFixture
{
    LTSGenerator tierTwoGenerator;
    
    @Before
    public void Setup()
    {
        tierTwoGenerator = new LTSGenerator();
        ltsGenerator.AddSOSRule(new LowPriorityMessageTakeRule(tierTwoGenerator));
    }
    
    @Test
    public void ExecutesALowPriorityMessageAtomically()
    {
        Actor actor = new ActorBuilder().WithID("actor").Build();
        
        Message message = new MessageWithBody(StatementsFrom(new EmptyStatement("s1"), new EmptyStatement("s2")));
        
        ActorRunTimeStateBuilder actorState = new ActorRunTimeStateBuilder()
                .WithActor(actor)
                .EnqueueLowPriorityMessage(message);
        
        globalState
                .AddActorRunTimeState(actorState);
        
        tierTwoGenerator.AddSOSRule(new EmptyStatementRule());
        generatedLTS = ltsGenerator.Generate(globalState.Build());
        
        GlobalRunTimeState nextGlobalState = globalState.Build();
        ActorRunTimeState nextActorState = nextGlobalState.FindActorState(actor);
        nextActorState.LowPriorityMessageQueue().Dequeue();

        assertTrue(generatedLTS.HasTransition(globalState.Build(), new TauLabel(), nextGlobalState));
    }
    
    @Test
    public void RetainesResetsInIntenalTransitions()
    {
        Actor actor = new ActorBuilder().WithID("actor").Build();
        
        ContinuousVariable cVar1 = new ContinuousVariable("cVar1");
        ContinuousVariable cVar2 = new ContinuousVariable("cVar2");
        
        Message message = new MessageWithBody(StatementsFrom(
                new ContinuousAssignmentStatement(cVar1, new ConstantExpression(1.0f))
                , new ContinuousAssignmentStatement(cVar2, new ConstantExpression(1.5f))
                , new ContinuousAssignmentStatement(cVar1, new ConstantExpression(2.0f))));
        
        ActorRunTimeStateBuilder actorState = new ActorRunTimeStateBuilder()
                .WithActor(actor)
                .EnqueueLowPriorityMessage(message);
        
        globalState
                .AddActorRunTimeState(actorState);
        
        tierTwoGenerator.AddSOSRule(new ContinuousAssignmentRule());
        generatedLTS = ltsGenerator.Generate(globalState.Build());
        
        GlobalRunTimeState nextGlobalState = globalState.Build();
        ActorRunTimeState nextActorState = nextGlobalState.FindActorState(actor);
        nextActorState.LowPriorityMessageQueue().Dequeue();

        TauLabel label = new TauLabel(Reset.ResetsFrom(
            new Reset(cVar1, new ConstantExpression(2.0f))
            ,new Reset(cVar2, new ConstantExpression(1.5f))));
        assertTrue(generatedLTS.HasTransition(globalState.Build(), label, nextGlobalState));
    }
}
