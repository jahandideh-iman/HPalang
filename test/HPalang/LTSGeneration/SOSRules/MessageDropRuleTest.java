/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.SOSRules;

import Builders.ActorBuilder;
import Builders.ActorRunTimeStateBuilder;
import HPalang.Core.SoftwareActor;
import HPalang.Core.Message;
import HPalang.Core.MessageHandler;
import HPalang.Core.Messages.NormalMessage;
import HPalang.Core.Statements.SendStatement;
import HPalang.LTSGeneration.RunTimeStates.ExecutionQueueState;
import HPalang.LTSGeneration.RunTimeStates.GlobalRunTimeState;
import HPalang.LTSGeneration.Labels.SoftwareLabel;
import Mocks.DirectActorLocator;
import Mocks.EmptyMessage;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 *
 * @author Iman Jahandideh
 */
public class MessageDropRuleTest extends SOSRuleTestFixture
{
    @Before
    public void Setup()
    {
        ltsGenerator.AddSOSRule(new MessageDropRule());
    }
    
    @Test
    public void ForEachActorStateIfThereIsSendStatementAndRecieverCapacityIsFullThenDropsTheMessage()
    {
        SoftwareActor actor1 = new ActorBuilder().WithID("actor1").WithCapacity(1).WithHandler("a", new MessageHandler()).Build();
        SoftwareActor actor2 = new ActorBuilder().WithID("actor2").WithCapacity(1).WithHandler("b", new MessageHandler()).Build();
        
        Message messageTo2 = new NormalMessage(actor2.Type().FindMessageHandler("b"));
        Message messageTo1 = new NormalMessage(actor1.Type().FindMessageHandler("a"));

        ActorRunTimeStateBuilder actor1State = new ActorRunTimeStateBuilder()
                .WithActor(actor1)
                .EnqueueStatement(new SendStatement( new DirectActorLocator(actor2), messageTo2))
                .EnqueueLowPriorityMessage(new EmptyMessage());
        
        ActorRunTimeStateBuilder actor2State = new ActorRunTimeStateBuilder()
                .WithActor(actor2)
                .EnqueueStatement(new SendStatement(new DirectActorLocator(actor1), messageTo1))
                .EnqueueLowPriorityMessage(new EmptyMessage());
        
        globalState.DiscreteState().AddSoftwareActorState(actor1State.Build());
        globalState.DiscreteState().AddSoftwareActorState(actor2State.Build());
                
                  
        generatedLTS = ltsGenerator.Generate(globalState);
        
        GlobalRunTimeState stateAfterMessageTo2Sent = globalState.DeepCopy();
        stateAfterMessageTo2Sent.DiscreteState().FindActorState(actor1).FindSubState(ExecutionQueueState.class).Statements().Dequeue();
        
        GlobalRunTimeState sateAfterMessageTo1Sent = globalState.DeepCopy();
        sateAfterMessageTo1Sent.DiscreteState().FindActorState(actor2).FindSubState(ExecutionQueueState.class).Statements().Dequeue();
        
        assertTrue(generatedLTS.HasTransition(globalState, new SoftwareLabel(), stateAfterMessageTo2Sent));
        assertTrue(generatedLTS.HasTransition(globalState, new SoftwareLabel(), stateAfterMessageTo2Sent));
    } 
    
}
