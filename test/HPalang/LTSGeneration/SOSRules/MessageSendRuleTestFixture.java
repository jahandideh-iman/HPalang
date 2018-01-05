/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.SOSRules;

import HPalang.Core.DiscreteExpressions.ConstantDiscreteExpression;
import HPalang.Core.DiscreteExpressions.VariableExpression;
import HPalang.Core.*;
import HPalang.LTSGeneration.RunTimeStates.GlobalRunTimeState;
import HPalang.LTSGeneration.Labels.SoftwareLabel;
import HPalang.Core.Statements.SendStatement;
import HPalang.LTSGeneration.Label;
import HPalang.LTSGeneration.Labels.Reset;
import HPalang.LTSGeneration.RunTimeStates.SoftwareActorState;
import Mocks.ComputableExpression;
import Mocks.ComputableVariable;
import HPalang.Core.ActorLocators.DirectActorLocator;
import Mocks.FakeVariable;
import Mocks.FakeMessage;
import HPalang.Core.NullExpression;
import Mocks.SingleRealVariablePoolMock;
import Mocks.TransitionCollectorMock;
import Mocks.UncomputableExpression;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.equalTo;
import org.junit.Before;
import static TestUtilities.CoreUtility.*;
import static TestUtilities.NetworkingUtility.*;
import HPalang.Core.Variables.RealVariable;

/**
 *
 * @author Iman Jahandideh
 */
public abstract class MessageSendRuleTestFixture extends SOSRuleTestFixture
{
    protected TransitionCollectorMock transitionCollectorMock = new TransitionCollectorMock();
    
    protected SoftwareActorState receiverState = CreateSoftwareActorState("receiver");
    protected SoftwareActorState senderState = CreateSoftwareActorState("sender");
    
    protected SoftwareActor receiver;
    protected SoftwareActor sender;
    
    protected CommunicationType communicationType;
    
    public void SetupSenderAndReceiver()
    {
        receiver = receiverState.SActor();
        sender = senderState.SActor();
        
        globalState.DiscreteState().AddSoftwareActorState(senderState);
        globalState.DiscreteState().AddSoftwareActorState(receiverState);
    }
        
    @Test
    public void IsNotAppliedWhenActorIsSuspended()
    {  
        SendStatement sendStatement = CreateEmptySendStatementTo(receiver);
        senderState.SetSuspended(true);
        senderState.Actor().SetCommunicationType(receiver, communicationType); 
        EnqueueStatement(sendStatement, senderState);
        
        ApplyRuleOn(globalState);

        transitionCollectorChecker.ExpectNoTransition();
        VerifyEqualOutputForMultipleApply(SimpleStateInfo(globalState));
    } 
            
    @Test
    public void AddsArgumentsValuesToMessageIfArgumentsAreComputable()
    {
        int value = 13;
        VariableParameter parameter = new VariableParameter(new ComputableVariable("param"));
        VariableArgument argument = new VariableArgument(parameter.Type(), new ComputableExpression(value));
        
        Message message = new FakeMessage(Statement.EmptyStatements(),MessageParameters.From(parameter));
        
        SendStatement sendStatement = CreateSendStatement(receiverState.SActor(), message, MessageArguments.From(argument));

        sender.SetCommunicationType(receiver, communicationType);
        senderState.ExecutionQueueState().Statements().Enqueue(sendStatement);

        ApplyRuleOn(globalState);
        rule.TryApply(SimpleStateInfo(globalState), transitionCollectorMock);
        
        VariableArgument expectedArgument =  FindSentLastPacket(receiverState.SActor(),transitionCollectorMock.GetState(0)).
                Arguments().AsList().get(0);
        
        assertThat(expectedArgument.Value(), equalTo(new ConstantDiscreteExpression(value)));
        VerifyEqualOutputForMultipleApply(SimpleStateInfo(globalState));
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

        
        sender.SetCommunicationType(receiver, communicationType);
        senderState.ExecutionQueueState().Statements().Enqueue(sendStatement);
        
        globalState.VariablePoolState().SetPool(new SingleRealVariablePoolMock(pooledVariable));

        ApplyAndVerifyRuleOn(globalState, transitionCollectorMock);
        
        VariableArgument generatedArgument =  FindSentLastPacket(receiverState.Actor(),transitionCollectorMock.GetState(0)).
                Arguments().AsList().get(0);
        
        Label expectedLabel = new SoftwareLabel(Reset.From(new Reset(pooledVariable, partialValue)));
        
        assertThat(generatedArgument.Value(), equalTo(new VariableExpression(pooledVariable)));
        assertThat(transitionCollectorMock.GetLabel(0), equalTo(expectedLabel));
    }
    
        
    protected abstract MessagePacket FindSentLastPacket(Actor actor, GlobalRunTimeState globalState);
        
}
