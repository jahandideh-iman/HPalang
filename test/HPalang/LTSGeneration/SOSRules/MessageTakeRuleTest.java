/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.SOSRules;

import Builders.ActorBuilder;
import HPalang.Core.Actor;
import HPalang.LTSGeneration.LTSGenerator;
import HPalang.LTSGeneration.LTSGenerator;
import HPalang.LTSGeneration.LabeledTransitionSystem;
import HPalang.LTSGeneration.Message;
import HPalang.LTSGeneration.MessageWithBody;
import HPalang.LTSGeneration.RunTimeStates.ActorRunTimeState;
import HPalang.LTSGeneration.RunTimeStates.GlobalRunTimeState;
import HPalang.Statements.Statement;
import Mocks.EmptyMessage;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Iman Jahandideh
 */
public class MessageTakeRuleTest
{
    public class GlobalRunTimeStateBuilder
    {
        private List<ActorRunTimeStateBuilder> actorRunTimeStateBuilders = new ArrayList<>();
        
        public GlobalRunTimeStateBuilder AddActorRunTimeState(ActorRunTimeStateBuilder actorRunTimeStateBuilder)
        {
            actorRunTimeStateBuilders.add(actorRunTimeStateBuilder);
            return this;
        }
        
        public GlobalRunTimeState Build()
        {
            GlobalRunTimeState globalState = new GlobalRunTimeState();
            
            for(ActorRunTimeStateBuilder builder : actorRunTimeStateBuilders)
                globalState.AddActorRunTimeState(builder.Build());
            
            return globalState;
        }
        
    }
    
    public class ActorRunTimeStateBuilder
    {
        private Actor actor;
        
        private Queue<Message> messages = new LinkedList<>();
        
        public ActorRunTimeStateBuilder WithActor(Actor actor)
        {
            this.actor = actor;
            return this;
        }
        
        public ActorRunTimeStateBuilder EnqueueMessage(Message message)
        {
            messages.add(message);
            return this;
        }
        
        public ActorRunTimeState Build()
        {
            ActorRunTimeState actorState = new ActorRunTimeState(actor);
            
            for(Message m : messages)
                actorState.EnqueueMessage(m);
            
            return actorState;
        }
    }
    
    @Test
    public void ForEachActorStateIfThereIsNoStatementAndThereAreMessagesThenEnqueuesOneMessage()
    {
        Actor actor1 = new ActorBuilder().WithID("actor1").WithCapacity(1).Build();
        Actor actor2 = new ActorBuilder().WithID("acto2").WithCapacity(1).Build();

        ActorRunTimeStateBuilder actor1State = new ActorRunTimeStateBuilder()
                .WithActor(actor1)
                .EnqueueMessage(new EmptyMessage());
        
        ActorRunTimeStateBuilder actor2State = new ActorRunTimeStateBuilder()
                .WithActor(actor2)
                .EnqueueMessage(new EmptyMessage());
        
        GlobalRunTimeStateBuilder globalState = new GlobalRunTimeStateBuilder()
                .AddActorRunTimeState(actor1State)
                .AddActorRunTimeState(actor2State);
             
        MessageTakeRule rule = new MessageTakeRule();
        
        LTSGenerator ltsGenerator = new LTSGenerator();
        
        ltsGenerator.AddSOSRule(rule);
        
        LabeledTransitionSystem lts = ltsGenerator.Generate(globalState.Build());
        
        GlobalRunTimeState globalStateAfterActor1MessageTake = globalState.Build();
        globalStateAfterActor1MessageTake.FindActorState(actor1).DequeueNextMessage();
        
        GlobalRunTimeState globalStateAfterActor2MessageTake = globalState.Build();
        globalStateAfterActor1MessageTake.FindActorState(actor2).DequeueNextMessage();
        
        
        assertTrue(lts.HasState(globalStateAfterActor1MessageTake));
        assertTrue(lts.HasState(globalStateAfterActor2MessageTake));
    } 
}
