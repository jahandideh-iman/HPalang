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
import HPalang.Core.PhysicalActor;
import HPalang.Core.Variables.FloatVariable;
import HPalang.Core.Variables.RealVariable;
import HPalang.LTSGeneration.RunTimeStates.ActorState;
import HPalang.LTSGeneration.RunTimeStates.PhysicalActorState;
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
public class PhysicalFIFOMessageTakeRuleTest extends SOSRuleTestFixture
{
    PhysicalActor arbitrarySender = CreatePhysicalActor("sender");
    
    PhysicalActorState receiverState = CreatePhysicalActorState("receiver");
    PhysicalActor receiver;
    
    @Before
    public void Setup()
    {
        rule = new PhysicalActorFIFOMessageTakeRule();
        
        receiver = receiverState.PActor();
        
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
        
        ApplyRuleOn(globalState);
        
        transitionCollectorChecker.ExpectTransition(new SoftwareLabel(), expectedGlobalState);
        VerifyEqualOutputForMultipleApply(SimpleStateInfo(globalState));
    }
    
    // TODO: Refactor the assertion.
    @Test
    public void AddsAnAssignmentStatementAtTheBeginingForEachArgument()
    {
        VariableParameter param1 = new VariableParameter(new FloatVariable("param1"));
        VariableParameter param2 = new VariableParameter(new FloatVariable("param2"));
        
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
        
        ApplyRuleOn(globalState);
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
        
        ApplyRuleOn(globalState);
        //rule.TryApply(SimpleStateInfo(globalState), transitionCollectorChecker);
        
        transitionCollectorChecker.ExpectTransition(new SoftwareLabel(), expectedGlobalState);
        VerifyEqualOutputForMultipleApply(SimpleStateInfo(globalState));
    }
    
    @Test
    public void TakeTheDataMessagesFirst()
    {
        PhysicalActorState actorState =  CreatePhysicalActorState("actor");
        Message controlMessage1 =  new EmptyMessage("controlMessage1", MessageType.Control);
        Message dataMessage = new EmptyMessage("dataMessage", MessageType.Data);
        Message controlMessage2 =  new EmptyMessage("controlMessag2", MessageType.Control);
        
        MessagePacket controlMessagePacket1 = MessagePacket(arbitrarySender, actorState.Actor(), controlMessage1);
        MessagePacket dataMessagePacket = MessagePacket(arbitrarySender, actorState.Actor(), dataMessage);
        MessagePacket controlMessagePacket2 = MessagePacket(arbitrarySender, actorState.Actor(), controlMessage2);


        PutMessagePacketInActor(controlMessagePacket1, actorState);
        PutMessagePacketInActor(dataMessagePacket, actorState);
        PutMessagePacketInActor(controlMessagePacket2, actorState);
        
        globalState.ContinuousState().AddPhysicalActorState(actorState);
        
        ApplyRuleOn(globalState);
        //rule.TryApply(SimpleStateInfo(globalState), transitionCollectorChecker);
        
        MessageQueueState expectedMessageQueue = FindActorState(actorState.PActor(), globalState.DeepCopy()).MessageQueueState();
        RemoveMessagePacket(dataMessagePacket, expectedMessageQueue);
        
        MessageQueueState actualMessageQueue = FindActorState(actorState.PActor(), CollectedGlobalState()).MessageQueueState();
        
        assertThat(actualMessageQueue, equalTo(expectedMessageQueue));
        VerifyEqualOutputForMultipleApply(SimpleStateInfo(globalState));
    }
    
    private GlobalRunTimeState ExpectedGlobalStateWhenMessageIsTaken(GlobalRunTimeState originalState, ActorState senderState , Message message)
    {
        GlobalRunTimeState expectedGlobalState = originalState.DeepCopy();
        ActorState expectedActorState = expectedGlobalState.FindActorState(senderState.Actor());
        MessagePacket packet =  expectedActorState.MessageQueueState().Messages().Dequeue();
        
        List<VariableParameter> parametersList = message.Parameters().AsList();
        List<VariableArgument> argumentsList = packet.Arguments().AsList();
        for(int i = 0 ; i < parametersList.size(); i++ )
            expectedActorState.ExecutionQueueState().Statements().Enqueue(
                    new AssignmentStatement(parametersList.get(i).Variable(), argumentsList.get(i).Value()));
        
        expectedActorState.ExecutionQueueState().Statements().Enqueue(message.GetMessageBody());
        

        expectedActorState.ExecutionQueueState().Statements().Enqueue(
                new MessageTeardownStatement(message.Parameters(), packet.PooledVariables()));
        
        return expectedGlobalState;
    }
    

}
