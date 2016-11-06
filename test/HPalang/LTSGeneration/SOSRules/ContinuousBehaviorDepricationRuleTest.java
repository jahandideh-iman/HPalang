/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.SOSRules;

import Builders.ActorBuilder;
import Builders.ActorRunTimeStateBuilder;
import Builders.GlobalRunTimeStateBuilder;
import HPalang.Core.Actor;
import HPalang.LTSGeneration.ConditionalLabel;
import HPalang.LTSGeneration.LTSGenerator;
import HPalang.LTSGeneration.LabeledTransitionSystem;
import HPalang.LTSGeneration.MessageWithBody;
import HPalang.LTSGeneration.RunTimeStates.ActorRunTimeState;
import HPalang.LTSGeneration.RunTimeStates.ContinuousBehavior;
import HPalang.LTSGeneration.RunTimeStates.GlobalRunTimeState;
import HPalang.LTSGeneration.TauLabel;
import HPalang.Statements.ResumeStatement;
import HPalang.Statements.Statement;
import static HPalang.Statements.Statement.StatementsFrom;
import Mocks.EmptyMessage;
import Mocks.EmptyStatement;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 *
 * @author Iman Jahandideh
 */
public class ContinuousBehaviorDepricationRuleTest
{
    LTSGenerator ltsGenerator = new LTSGenerator();
    LabeledTransitionSystem generatedLTS;
    GlobalRunTimeStateBuilder globalState = new GlobalRunTimeStateBuilder();
    
    @Before
    public void Setup()
    {
        ltsGenerator.AddSOSRule(new ContinuousBehaviorDepricationRule());
    }
    
    @Test
    public void ForEachContinousBehaviorAddsDeprication()
    {
        Actor actor1 = new ActorBuilder().WithID("actor1").WithCapacity(1).Build();
       
        ActorRunTimeStateBuilder actor1State = new ActorRunTimeStateBuilder()
                .WithActor(actor1)
                .AddBehavior(new ContinuousBehavior("inv1","ode1","guard1",StatementsFrom(new EmptyStatement())))
                .AddBehavior(new ContinuousBehavior("inv2","ode2","guard2",StatementsFrom(new EmptyStatement())));
        
        
        globalState
                .AddActorRunTimeState(actor1State);
                
                 
        generatedLTS = ltsGenerator.Generate(globalState.Build());
        
        GlobalRunTimeState nextGlobalState1 = globalState.Build();
        ActorRunTimeState nextActorState1 = nextGlobalState1.FindActorState(actor1);
        nextActorState1.GetContinuousState().RemoveBehavior(new ContinuousBehavior("inv1","ode1","guard1",StatementsFrom(new EmptyStatement())));
        nextActorState1.GetDiscreteState().EnqueueMessage(new MessageWithBody(StatementsFrom(new EmptyStatement())));
        
        GlobalRunTimeState nextGlobalState2 = globalState.Build();
        ActorRunTimeState nextActorState2 = nextGlobalState2.FindActorState(actor1);
        nextActorState2.GetContinuousState().RemoveBehavior(new ContinuousBehavior("inv2","ode2","guard2",StatementsFrom(new EmptyStatement())));
        nextActorState2.GetDiscreteState().EnqueueMessage(new MessageWithBody(StatementsFrom(new EmptyStatement())));

        assertTrue(generatedLTS.HasTransition(globalState.Build(), new ConditionalLabel("guard1"), nextGlobalState1));
        assertTrue(generatedLTS.HasTransition(globalState.Build(), new ConditionalLabel("guard2"), nextGlobalState2));
    }
}
