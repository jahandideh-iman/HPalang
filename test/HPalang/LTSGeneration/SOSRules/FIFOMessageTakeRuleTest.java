/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.SOSRules;

import HPalang.Core.Message;
import HPalang.Core.Message.MessageType;
import HPalang.Core.MessageArguments;
import HPalang.Core.MessagePacket;
import HPalang.Core.MessageParameters;
import HPalang.Core.SoftwareActor;
import static HPalang.Core.Statement.StatementsFrom;
import HPalang.Core.Statements.AssignmentStatement;
import HPalang.Core.Statements.MessageTeardownStatement;
import HPalang.Core.VariableArgument;
import HPalang.Core.VariableParameter;
import HPalang.Core.Variables.IntegerVariable;
import HPalang.LTSGeneration.RunTimeStates.GlobalRunTimeState;
import HPalang.LTSGeneration.Labels.SoftwareLabel;
import HPalang.LTSGeneration.RunTimeStates.MessageQueueState;
import HPalang.LTSGeneration.RunTimeStates.SoftwareActorState;
import Mocks.EmptyMessage;
import static TestUtilities.CoreUtility.*;
import static TestUtilities.NetworkingUtility.*;
import Mocks.EmptyStatement;
import Mocks.FakeMessage;
import HPalang.Core.NullExpression;
import static TestUtilities.CoreUtility.SimpleStateInfo;
import java.util.List;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;

/**
 *
 * @author Iman Jahandideh
 */

// TODO: Find a better way for testing the expected outcome of the tests
public class FIFOMessageTakeRuleTest extends SOSRuleTestFixture
{
    SoftwareActor arbitrarySender = CreateSofwareActor("sender");
    
    SoftwareActorState receiverState = CreateSoftwareActorState("receiver");
    SoftwareActor receiver;
    
    @Before
    public void Setup()
    {
        rule = new FIFOMessageTakeRule();
        
        receiver = receiverState.SActor();
        
        AddActorState(receiverState, globalState);
    }
    
    @Test
    public void TakesAMessageIfIsNotSuspendedAndExecutionQueueIsEmpty()
    {
        Message message = new FakeMessage(StatementsFrom(new EmptyStatement("s1"), new EmptyStatement("s2")));
        
        MessagePacket messagePacket = MessagePacket(
                arbitrarySender,
                receiver,
                message);
        
       
        PutMessagePacketInActor(messagePacket, receiverState);
        
        GlobalRunTimeState expectedGlobalState = 
                ExpectedGlobalStateWhenMessageIsTaken(globalState, receiverState, message);
        
        ApplyAndVerifyRuleOn(globalState);
        //rule.TryApply(SimpleStateInfo(globalState), transitionCollectorChecker);
        
        transitionCollectorChecker.ExpectTransition(new SoftwareLabel(), expectedGlobalState);
        VerifyEqualOutputForMultipleApply(SimpleStateInfo(globalState));
    }
    
    // TODO: Refactor the assertion.
    @Test
    public void AddsAnAssignmentStatementAtTheBeginingForEachArgument()
    {
        VariableParameter param1 = new VariableParameter(new IntegerVariable("param1"));
        VariableParameter param2 = new VariableParameter(new IntegerVariable("param2"));
        
        VariableArgument arg1 = new VariableArgument(param1.Type(), new NullExpression("e1"));
        VariableArgument arg2 = new VariableArgument(param2.Type(), new NullExpression("e2"));
        
        Message message = new FakeMessage(
                StatementsFrom(new EmptyStatement("s1")),
                MessageParameters.From(param1, param2));
        
       
        
        MessagePacket messagePacket = MessagePacket(
                arbitrarySender, 
                receiver,
                message, 
                MessageArguments.From(arg1, arg2));
        
        PutMessagePacketInActor(messagePacket, receiverState);
        
        GlobalRunTimeState expectedGlobalState = ExpectedGlobalStateWhenMessageIsTaken(globalState, receiverState, message);
        
        ApplyAndVerifyRuleOn(globalState);
        //rule.TryApply(SimpleStateInfo(globalState), transitionCollectorChecker);
        
        transitionCollectorChecker.ExpectTransition(new SoftwareLabel(), expectedGlobalState);
        VerifyEqualOutputForMultipleApply(SimpleStateInfo(globalState));
    }
    
    
    // TODO: Refactor the assertion
    @Test
    public void AddsMessageArgumentsToActorsValuation()
    {
        VariableParameter param1 = new VariableParameter(new IntegerVariable("param1"));
        VariableParameter param2 = new VariableParameter(new IntegerVariable("param2"));
        
        VariableArgument arg1 = new VariableArgument(param1.Type(), new NullExpression("e1"));
        VariableArgument arg2 = new VariableArgument(param2.Type(), new NullExpression("e2"));
        
        Message message = new FakeMessage(
                StatementsFrom(new EmptyStatement("s1")),
                MessageParameters.From(param1, param2));
        
        MessagePacket messagePacket = MessagePacket(
                arbitrarySender,
                receiver,
                message,
                MessageArguments.From(arg1, arg2));
        
        PutMessagePacketInActor(messagePacket, receiverState);

        GlobalRunTimeState expectedGlobalState = ExpectedGlobalStateWhenMessageIsTaken(globalState, receiverState, message);
        
        ApplyAndVerifyRuleOn(globalState);
        //rule.TryApply(SimpleStateInfo(globalState), transitionCollectorChecker);
        
        transitionCollectorChecker.ExpectTransition(new SoftwareLabel(), expectedGlobalState);
        VerifyEqualOutputForMultipleApply(SimpleStateInfo(globalState));
    }

    @Test
    public void AddsATearDownStatementAfterMessageBodyToRemoveMessageParametersFromValuation()
    {
        VariableParameter param = new VariableParameter(new IntegerVariable("param"));
        VariableArgument arg = new VariableArgument(param.Type(), new NullExpression("e2"));
        
        Message message = new FakeMessage(
                StatementsFrom(new EmptyStatement("s1")),
                MessageParameters.From(param));
        
        MessagePacket messagePacket = MessagePacket(
                arbitrarySender,
                receiver,
                message,
                MessageArguments.From(arg));
        
        PutMessagePacketInActor(messagePacket, receiverState);

        GlobalRunTimeState expectedGlobalState = ExpectedGlobalStateWhenMessageIsTaken(globalState, receiverState, message);
        
        ApplyAndVerifyRuleOn(globalState);
        //rule.TryApply(SimpleStateInfo(globalState), transitionCollectorChecker);
        
        transitionCollectorChecker.ExpectTransition(new SoftwareLabel(), expectedGlobalState);
        VerifyEqualOutputForMultipleApply(SimpleStateInfo(globalState));
    }
    
    @Test
    public void TakeTheDataMessagesFirst()
    {
        SoftwareActorState actorState =  CreateSoftwareActorState("actor");
        Message controlMessage1 =  new EmptyMessage("controlMessage1", MessageType.Control);
        Message dataMessage = new EmptyMessage("dataMessage", MessageType.Data);
        Message controlMessage2 =  new EmptyMessage("controlMessag2", MessageType.Control);
        
        MessagePacket controlMessagePacket1 = MessagePacket(arbitrarySender, actorState.SActor(), controlMessage1);
        MessagePacket dataMessagePacket = MessagePacket(arbitrarySender, actorState.SActor(), dataMessage);
        MessagePacket controlMessagePacket2 = MessagePacket(arbitrarySender, actorState.SActor(), controlMessage2);


        PutMessagePacketInActor(controlMessagePacket1, actorState);
        PutMessagePacketInActor(dataMessagePacket, actorState);
        PutMessagePacketInActor(controlMessagePacket2, actorState);
        
        globalState.DiscreteState().AddSoftwareActorState(actorState);
        
        ApplyAndVerifyRuleOn(globalState);
        //rule.TryApply(SimpleStateInfo(globalState), transitionCollectorChecker);
        
        MessageQueueState expectedMessageQueue = FindActorState(actorState.SActor() , globalState.DeepCopy()).MessageQueueState();
        RemoveMessagePacket(dataMessagePacket, expectedMessageQueue);
        
        MessageQueueState actualMessageQueue = FindActorState(actorState.SActor(), CollectedGlobalState()).MessageQueueState();
        
        assertThat(actualMessageQueue, equalTo(expectedMessageQueue));
        VerifyEqualOutputForMultipleApply(SimpleStateInfo(globalState));
    }
    
    private GlobalRunTimeState ExpectedGlobalStateWhenMessageIsTaken(GlobalRunTimeState originalState, SoftwareActorState senderState , Message message)
    {
        GlobalRunTimeState expectedGlobalState = originalState.DeepCopy();
        SoftwareActorState expectedActorState = expectedGlobalState.DiscreteState().FindActorState(senderState.SActor());
        MessagePacket packet =  expectedActorState.MessageQueueState().Messages().Dequeue();
        
        List<VariableParameter> parametersList = message.Parameters().AsList();
        List<VariableArgument> argumentsList = packet.Arguments().AsList();
        for(int i = 0 ; i < parametersList.size(); i++ )
            expectedActorState.ExecutionQueueState().Statements().Enqueue(
                    new AssignmentStatement(parametersList.get(i).Variable(), argumentsList.get(i).Value()));
        
        expectedActorState.ExecutionQueueState().Statements().Enqueue(message.GetMessageBody());
        
        for (VariableParameter parameter : message.Parameters().AsSet()) {
            expectedActorState.ValuationState().Valuation().Add(parameter.Variable());
        }
        expectedActorState.ExecutionQueueState().Statements().Enqueue(
                new MessageTeardownStatement(message.Parameters(), packet.PooledVariables()));
        
        return expectedGlobalState;
    }
    
    private SoftwareActorState CreateBlankActorStateWith(MessagePacket packet)
    {
        SoftwareActorState actorState =  CreateSoftwareActorState("actor");
        actorState.SetSuspended(false);
        actorState.ExecutionQueueState().Statements().Clear();
        
        actorState.MessageQueueState().Messages().Enqueue(packet);
        return actorState;
    }
}
