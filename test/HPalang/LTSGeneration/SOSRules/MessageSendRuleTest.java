/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.SOSRules;

import HPalang.Core.DiscreteExpressions.ConstantDiscreteExpression;
import HPalang.Core.DiscreteExpressions.VariableExpression;
import HPalang.Core.Expression;
import HPalang.Core.SoftwareActor;
import HPalang.Core.Message;
import HPalang.Core.MessageArguments;
import HPalang.Core.MessagePacket;
import HPalang.Core.MessageParameters;
import HPalang.Core.Statement;
import HPalang.LTSGeneration.RunTimeStates.GlobalRunTimeState;
import HPalang.LTSGeneration.Labels.SoftwareLabel;
import HPalang.Core.Statements.SendStatement;
import HPalang.Core.VariableArgument;
import HPalang.Core.VariableParameter;
import HPalang.Core.Variables.RealVariable;
import HPalang.LTSGeneration.Label;
import HPalang.LTSGeneration.Labels.Reset;
import HPalang.LTSGeneration.RunTimeStates.SoftwareActorState;
import Mocks.ComputableExpression;
import Mocks.ComputableVariable;
import Mocks.DirectActorLocator;
import Mocks.FakeVariable;
import Mocks.FakeMessage;
import Mocks.NullExpression;
import Mocks.SingleRealVariablePoolMock;
import Mocks.TransitionCollectorMock;
import Mocks.UncomputableExpression;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.equalTo;
import org.junit.Before;
import static TestUtilities.CoreUtility.*;
import static TestUtilities.NetworkingUtility.*;
import static HPalang.Core.CommunicationType.*;

/**
 *
 * @author Iman Jahandideh
 */
public class MessageSendRuleTest extends SOSRuleTestFixture
{
    TransitionCollectorMock transitionCollectorMock = new TransitionCollectorMock();
    
    SoftwareActorState receiverState = CreateSoftwareActorState("receiver");
    SoftwareActorState senderState = CreateSoftwareActorState("sender");
    
    SoftwareActor receiver;
    SoftwareActor sender;
    
    @Before
    public void Setup()
    {
        rule = new MessageSendRule();
        
        receiver = receiverState.SActor();
        sender = senderState.SActor();
        
        globalState.DiscreteState().AddSoftwareActorState(senderState);
        globalState.DiscreteState().AddSoftwareActorState(receiverState);
    }
    
    @Test
    public void SendsMessagePacketToReceiverIfCommunicationTypeIsWired()
    {  
        SendStatement sendStatement = CreateEmptySendStatementTo(receiver);
        
        senderState.Actor().SetCommunicationType(receiver, Wire); 
        EnqueueStatement(sendStatement, senderState);
        
        rule.TryApply(SimpleStateInfo(globalState), transitionCollectorChecker);

        GlobalRunTimeState expectedGlobalState = globalState.DeepCopy();
        MessagePacket expectedPacket = MessagePacket(senderState, sendStatement);
        ClearStatementsFor(FindActorState(sender, expectedGlobalState));
        PutMessagePacketInActor(expectedPacket, FindActorState(receiver, expectedGlobalState));
        
        transitionCollectorChecker.ExpectTransition(new SoftwareLabel(), expectedGlobalState);
    } 

    
    @Test
    public void SendsMessagePacketToCanNetworkIfCommunicationTypeIsCAN()
    {
        SendStatement sendStatement = CreateEmptySendStatementTo(receiver);
        
        senderState.SActor().SetCommunicationType(receiver, CAN); 
        EnqueueStatement(sendStatement, senderState);
        
        rule.TryApply(SimpleStateInfo(globalState), transitionCollectorChecker);

        GlobalRunTimeState expectedGlobalState = globalState.DeepCopy();
        MessagePacket expectedPacket = MessagePacket(senderState, sendStatement);
        ClearStatementsFor(FindActorState(sender, expectedGlobalState));
        PutMessagePacketInNetworkState(expectedPacket, expectedGlobalState);
        
        transitionCollectorChecker.ExpectTransition(new SoftwareLabel(), expectedGlobalState);
    } 
    
    @Test
    public void DoesNotSendTheMessageIfTheRecieverMessageQueueIsFull()
    {
        SendStatement sendStatement = CreateEmptySendStatementTo(receiver);
        
        senderState.SActor().SetCommunicationType(receiver, CAN); 
        EnqueueStatement(sendStatement, senderState);
        FillActorsQeueue(receiverState);
        
        rule.TryApply(SimpleStateInfo(globalState), transitionCollectorChecker);
        
        transitionCollectorChecker.ExpectNoTransition();
    }
    
    @Test
    public void AddsArgumentsValuesToMessageIfArgumentsAreComputable()
    {
        int value = 13;
        VariableParameter parameter = new VariableParameter(new ComputableVariable("param"));
        VariableArgument argument = new VariableArgument(parameter.Type(), new ComputableExpression(value));
        
        Message message = new FakeMessage(Statement.EmptyStatements(),MessageParameters.From(parameter));
        
        SendStatement sendStatement = CreateSendStatement(receiverState.SActor(), message, MessageArguments.From(argument));

        sender.SetCommunicationType(receiver, Wire);
        senderState.ExecutionQueueState().Statements().Enqueue(sendStatement);

 
        rule.TryApply(SimpleStateInfo(globalState), transitionCollectorMock);
        
        VariableArgument expectedArgument =  FindLastPacket(receiverState.SActor(),transitionCollectorMock.collectedState).
                Arguments().AsList().get(0);
        
        assertThat(expectedArgument.Value(), equalTo(new ConstantDiscreteExpression(value)));
    }
    
    @Test
    public void AddsArgumentsPartialValuesToMessageIfArgumentsAreComputable()
    {
        RealVariable pooledVariable = new RealVariable("pooledVaraible");
        Expression partialValue = new NullExpression("exp");
        VariableParameter parameter = new VariableParameter(new FakeVariable("param"));
        VariableArgument argument = new VariableArgument(parameter.Type(), new UncomputableExpression(partialValue));
        
        Message message = new FakeMessage(Statement.EmptyStatements(),MessageParameters.From(parameter));
                
        SendStatement sendStatement = CreateSendStatement(receiverState.SActor(), message, MessageArguments.From(argument));

        
        sender.SetCommunicationType(receiver, Wire);
        senderState.ExecutionQueueState().Statements().Enqueue(sendStatement);
        
        globalState.VariablePoolState().SetPool(new SingleRealVariablePoolMock(pooledVariable));

       
        rule.TryApply(SimpleStateInfo(globalState), transitionCollectorMock);
        
        VariableArgument generatedArgument =  FindLastPacket(receiverState.Actor(),transitionCollectorMock.collectedState).
                Arguments().AsList().get(0);
        
        Label expectedLabel = new SoftwareLabel(Reset.From(new Reset(pooledVariable, partialValue)));
        
        assertThat(generatedArgument.Value(), equalTo(new VariableExpression(pooledVariable)));
        assertThat(transitionCollectorMock.collectedLabel, equalTo(expectedLabel));
    }
    
    

}
