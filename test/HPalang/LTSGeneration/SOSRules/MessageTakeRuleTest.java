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
import HPalang.LTSGeneration.LTSGenerator;
import HPalang.LTSGeneration.LabeledTransitionSystem;
import HPalang.Core.Message;
import HPalang.Core.Messages.MessageWithBody;
import HPalang.LTSGeneration.RunTimeStates.ActorRunTimeState;
import HPalang.LTSGeneration.RunTimeStates.GlobalRunTimeState;
import HPalang.LTSGeneration.TauLabel;
import HPalang.Core.Statement;
import Mocks.EmptyMessage;
import Mocks.EmptyStatement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import static org.hamcrest.CoreMatchers.is;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 *
 * @author Iman Jahandideh
 */
public class MessageTakeRuleTest
{
    LTSGenerator ltsGenerator = new LTSGenerator();
    LabeledTransitionSystem generatedLTS;
    GlobalRunTimeStateBuilder globalState = new GlobalRunTimeStateBuilder();
    
    @Before
    public void Setup()
    {
        ltsGenerator.AddSOSRule(new MessageTakeRule());
    }
    
    @Test
    public void ForEachActorStateIfThereIsNoStatementAndThereAreMessagesThenDequeuesOneMessage()
    {
        Actor actor1 = new ActorBuilder().WithID("actor1").WithCapacity(1).Build();
        Actor actor2 = new ActorBuilder().WithID("acto2").WithCapacity(1).Build();

        ActorRunTimeStateBuilder actor1State = new ActorRunTimeStateBuilder()
                .WithActor(actor1)
                .EnqueueLowPriorityMessage(new EmptyMessage());
        
        ActorRunTimeStateBuilder actor2State = new ActorRunTimeStateBuilder()
                .WithActor(actor2)
                .EnqueueLowPriorityMessage(new EmptyMessage());
        
        globalState.AddActorRunTimeState(actor1State)
                .AddActorRunTimeState(actor2State);
                  
        generatedLTS = ltsGenerator.Generate(globalState.Build());
        
        GlobalRunTimeState globalStateAfterActor1MessageTake = globalState.Build();
        globalStateAfterActor1MessageTake.FindActorState(actor1).LowPriorityMessageQueue().Dequeue();
        
        GlobalRunTimeState globalStateAfterActor2MessageTake = globalState.Build();
        globalStateAfterActor2MessageTake.FindActorState(actor2).LowPriorityMessageQueue().Dequeue();
        
        assertTrue(generatedLTS.HasTransition(globalState.Build(), new TauLabel(), globalStateAfterActor1MessageTake));
        assertTrue(generatedLTS.HasTransition(globalState.Build(), new TauLabel(),globalStateAfterActor2MessageTake));
    } 
    
    @Test
    public void AddsMessageBodyToStatementsWhenDequeuesAMessage()
    {
        Actor actor = new ActorBuilder().WithID("actor1").WithCapacity(1).Build();
        
        Queue<Statement> statements = new LinkedList<>();
        statements.add(new EmptyStatement());
        statements.add(new EmptyStatement());

        ActorRunTimeStateBuilder actorState = new ActorRunTimeStateBuilder()
                .WithActor(actor)
                .EnqueueLowPriorityMessage(new MessageWithBody(statements));
        
        globalState.AddActorRunTimeState(actorState);
             
        generatedLTS = ltsGenerator.Generate(globalState.Build());
        
        GlobalRunTimeState globalStateAfterActor1MessageTake = globalState.Build();
        globalStateAfterActor1MessageTake.FindActorState(actor).LowPriorityMessageQueue().Dequeue();
        globalStateAfterActor1MessageTake.FindActorState(actor).StatementQueue().Enqueue(statements);
        
        assertTrue(generatedLTS.HasState(globalStateAfterActor1MessageTake));
    }
}
