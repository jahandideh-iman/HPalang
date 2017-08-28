/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.SOSRules;

import Builders.SoftwareActorBuilder;
import Builders.SoftwareActorStateBuilder;
import HPalang.Core.DiscreteExpressions.ConstantDiscreteExpression;
import HPalang.Core.SoftwareActor;
import HPalang.Core.MessageHandler;
import HPalang.Core.Message;
import HPalang.Core.MessageArguments;
import HPalang.Core.MessagePacket;
import HPalang.Core.MessageParameters;
import HPalang.Core.Messages.NormalMessage;
import HPalang.Core.Statement;
import HPalang.LTSGeneration.RunTimeStates.GlobalRunTimeState;
import HPalang.LTSGeneration.Labels.SoftwareLabel;
import HPalang.Core.Statements.SendStatement;
import HPalang.Core.VariableArgument;
import HPalang.Core.VariableParameter;
import HPalang.LTSGeneration.RunTimeStates.ExecutionQueueState;
import HPalang.LTSGeneration.RunTimeStates.MessageQueueState;
import HPalang.LTSGeneration.RunTimeStates.SoftwareActorState;
import HPalang.LTSGeneration.Transition;
import Mocks.ComputableExpression;
import Mocks.ComputableVariable;
import Mocks.DirectActorLocator;
import Mocks.EmptyMessage;
import Mocks.FakeMessage;
import Mocks.TransitionCollectorMock;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.equalTo;
import org.junit.Before;
import static TestUtilities.Utilities.*;
import static TestUtilities.NetworkingUtility.*;

/**
 *
 * @author Iman Jahandideh
 */
public class MessageSendRuleTest extends SOSRuleTestFixture
{
    TransitionCollectorMock collectorMock = new TransitionCollectorMock();
    
    SoftwareActorState receiverState = CreateSoftwareActorState("receiver");
    SoftwareActorState senderState = CreateSoftwareActorState("sender");
    
    @Before
    public void Setup()
    {
        rule = new MessageSendRule();
        ltsGenerator.AddSOSRule(new MessageSendRule());
    }
    
    @Test
    public void ForEachActorStateIfThereIsSendStatementSendsTheMessage()
    {
        SoftwareActor actor1 = new SoftwareActorBuilder().WithID("actor1").Build();
        SoftwareActor actor2 = new SoftwareActorBuilder().WithID("actor2").Build();
        
        Message messageTo2 = new EmptyMessage("to2");
        Message messageTo1 = new EmptyMessage("to1");

        SoftwareActorStateBuilder actor1State = new SoftwareActorStateBuilder()
                .WithActor(actor1)
                .EnqueueStatement(new SendStatement(new DirectActorLocator(actor2), messageTo2));
        
        SoftwareActorStateBuilder actor2State = new SoftwareActorStateBuilder()
                .WithActor(actor2)
                .EnqueueStatement(new SendStatement(new DirectActorLocator(actor1), messageTo1));
        
        globalState.DiscreteState().AddSoftwareActorState(actor1State.Build());
        globalState.DiscreteState().AddSoftwareActorState(actor2State.Build());

        generatedLTS = ltsGenerator.Generate(globalState);
        
        GlobalRunTimeState stateAfterMessageTo2Sent = globalState.DeepCopy();
        stateAfterMessageTo2Sent.DiscreteState().FindActorState(actor1).FindSubState(ExecutionQueueState.class).Statements().Dequeue();
        stateAfterMessageTo2Sent.DiscreteState().FindActorState(actor2).MessageQueueState().Messages().
                Enqueue(new MessagePacket(actor1, actor2, messageTo2, MessageArguments.From()));
        
        GlobalRunTimeState sateAfterMessageTo1Sent = globalState.DeepCopy();
        sateAfterMessageTo1Sent.DiscreteState().FindActorState(actor2).FindSubState(ExecutionQueueState.class).Statements().Dequeue();
          sateAfterMessageTo1Sent.DiscreteState().FindActorState(actor1).MessageQueueState().Messages().
                Enqueue(new MessagePacket(actor2, actor1, messageTo1, MessageArguments.From()));
        //sateAfterMessageTo1Sent.DiscreteState().FindActorState(actor1).FindSubState(MessageQueueState.class).Messages().Enqueue(messageTo1);
        
        assertTrue(generatedLTS.HasTransition(globalState, new SoftwareLabel(), stateAfterMessageTo2Sent));
        assertTrue(generatedLTS.HasTransition(globalState, new SoftwareLabel(), stateAfterMessageTo2Sent));
    } 
    
    @Test
    public void DoesNotSendTheMessageIfTheRecieverMessageQueueIsFull()
    {
        int capacity = 1;

        SoftwareActorState fullCapacityActorState = CreateSoftwareActorState("receiver",capacity);
        SoftwareActor actor = fullCapacityActorState.Actor();
        fullCapacityActorState.ExecutionQueueState().Statements().Enqueue(new SendStatement(new DirectActorLocator(actor), new EmptyMessage("Message2")));
        fullCapacityActorState.MessageQueueState().Messages().Enqueue(EmptySelfPacketFor(actor));
        
        globalState.DiscreteState().AddSoftwareActorState(fullCapacityActorState);
                
                  
        generatedLTS = ltsGenerator.Generate(globalState);
       
        
        List<Transition> transitions =  generatedLTS.GetOutTransitionsFor(globalState);
        
        assertThat(transitions.size(), equalTo(0));
    }
    
    @Test
    public void AddsTheArgumentValuesToMessageIfArgumentsAreComputable()
    {
        int value = 13;
        VariableParameter parameter = new VariableParameter(new ComputableVariable("param"));
        VariableArgument argument = new VariableArgument(parameter, new ComputableExpression(value));
        
        Message message = new FakeMessage(Statement.EmptyStatements(),MessageParameters.From(parameter));
                
        SendStatement sendStatement = new SendStatement(
                new DirectActorLocator(receiverState.Actor()),
                message,
                MessageArguments.From(argument));
        
        senderState.ExecutionQueueState().Statements().Enqueue(sendStatement);
        
        globalState.DiscreteState().AddSoftwareActorState(senderState);
        globalState.DiscreteState().AddSoftwareActorState(receiverState);

       
        rule.TryApply(SimpleStateInfo(globalState), collectorMock);
        
        VariableArgument expectedArgument =  FindLastPacket(receiverState.Actor(),collectorMock.collectedState).
                Arguments().ArgumentFor(parameter);
        
        assertThat(expectedArgument.Value(), equalTo(new ConstantDiscreteExpression(value)));
    }
    
    private MessagePacket FindLastPacket(SoftwareActor actor, GlobalRunTimeState globalState)
    {
        return globalState.DiscreteState().FindActorState(actor).MessageQueueState().Messages().Head();
    }
}
