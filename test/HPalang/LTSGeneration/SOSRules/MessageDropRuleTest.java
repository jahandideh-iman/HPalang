/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.SOSRules;

import Builders.SoftwareActorBuilder;
import Builders.SoftwareActorStateBuilder;
import HPalang.Core.SoftwareActor;
import HPalang.Core.Message;
import HPalang.Core.MessageHandler;
import HPalang.Core.Messages.NormalMessage;
import HPalang.Core.Statements.SendStatement;
import HPalang.LTSGeneration.RunTimeStates.ExecutionQueueState;
import HPalang.LTSGeneration.RunTimeStates.GlobalRunTimeState;
import HPalang.LTSGeneration.Labels.SoftwareLabel;
import HPalang.LTSGeneration.RunTimeStates.SoftwareActorState;
import Mocks.DirectActorLocator;
import Mocks.EmptyMessage;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import static TestUtilities.NetworkingUtility.*;

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
        SoftwareActor actor1 = new SoftwareActorBuilder().WithID("actor1").WithCapacity(0).WithHandler("a", new MessageHandler()).Build();
        SoftwareActor actor2 = new SoftwareActorBuilder().WithID("actor2").WithCapacity(0).WithHandler("b", new MessageHandler()).Build();
        
        Message messageTo2 = new NormalMessage(actor2.Type().FindMessageHandler("b"));
        Message messageTo1 = new NormalMessage(actor1.Type().FindMessageHandler("a"));

        SoftwareActorState actor1State = new SoftwareActorStateBuilder()
                .WithActor(actor1)
                .EnqueueStatement(CreateSendStatement(actor2, messageTo2)).Build();

        
        SoftwareActorState actor2State = new SoftwareActorStateBuilder()
                .WithActor(actor2)
                .EnqueueStatement(CreateSendStatement(actor1, messageTo1)).Build();
        
        globalState.DiscreteState().AddSoftwareActorState(actor1State);
        globalState.DiscreteState().AddSoftwareActorState(actor2State);
                
                  
        generatedLTS = ltsGenerator.Generate(globalState);
        
        GlobalRunTimeState stateAfterMessageTo2Sent = globalState.DeepCopy();
        stateAfterMessageTo2Sent.DiscreteState().FindActorState(actor1).FindSubState(ExecutionQueueState.class).Statements().Dequeue();
        
        GlobalRunTimeState sateAfterMessageTo1Sent = globalState.DeepCopy();
        sateAfterMessageTo1Sent.DiscreteState().FindActorState(actor2).FindSubState(ExecutionQueueState.class).Statements().Dequeue();
        
        assertTrue(generatedLTS.HasTransition(globalState, new SoftwareLabel(), stateAfterMessageTo2Sent));
        assertTrue(generatedLTS.HasTransition(globalState, new SoftwareLabel(), stateAfterMessageTo2Sent));
    } 
    
}
