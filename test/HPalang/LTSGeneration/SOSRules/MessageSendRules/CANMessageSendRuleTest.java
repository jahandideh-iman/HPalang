/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.SOSRules.MessageSendRules;

import HPalang.Core.Actor;
import static HPalang.Core.CommunicationType.CAN;
import HPalang.Core.Statements.SendStatement;
import HPalang.LTSGeneration.Labels.SoftwareLabel;
import HPalang.LTSGeneration.RunTimeStates.GlobalRunTimeState;
import HPalang.Core.MessagePacket;
import HPalang.LTSGeneration.RunTimeStates.ActorState;
import HPalang.LTSGeneration.SOSRules.MessageSendRuleTestFixture;
import HPalang.LTSGeneration.Utilities.CreationUtility;
import static HPalang.LTSGeneration.Utilities.CreationUtility.CreateDeadlockState;
import static HPalang.LTSGeneration.Utilities.CreationUtility.CreateDeadlockTransition;
import static TestUtilities.CoreUtility.*;
import static TestUtilities.NetworkingUtility.*;
import java.util.List;
import org.junit.Test;
import org.junit.Before;

/**
 *
 * @author Iman Jahandideh
 */
public class CANMessageSendRuleTest extends MessageSendRuleTestFixture
{
    @Before
    public void Setup()
    {
        SetupSenderAndReceiver();
        rule = new CANMessageSendRule();  
        communicationType = CAN;
    }
   
    @Override
    protected MessagePacket FindSentLastPacket(Actor actor, GlobalRunTimeState globalState)
    {
        List<MessagePacket>  buffer = globalState.NetworkState().Buffer();
        return buffer.get(buffer.size()-1);
    }
    
    @Test
    public void SendsMessagePacketToCanNetworkIfCommunicationTypeIsCAN()
    {
        SendStatement sendStatement = CreateEmptySendStatementTo(receiver);
        
        senderState.SActor().SetCommunicationType(receiver, CAN); 
        EnqueueStatement(sendStatement, senderState);
        
        ApplyRuleOn(globalState);

        GlobalRunTimeState expectedGlobalState = globalState.DeepCopy();
        MessagePacket expectedPacket = MessagePacket(senderState, sendStatement);
        ClearStatementsFor(FindActorState(sender, expectedGlobalState));
        PutMessagePacketInNetworkState(expectedPacket, expectedGlobalState);
        
        transitionCollectorChecker.ExpectTransition(new SoftwareLabel(), expectedGlobalState);
        VerifyEqualOutputForMultipleApply(SimpleStateInfo(globalState));
    } 
    
    @Test
    public void GoesToDeadlockIfAnotherInstanceOfMessageIsAlreadyBuffered()
    {
        SendStatement sendStatement = CreateEmptySendStatementTo(receiver);
        senderState.SActor().SetCommunicationType(receiver, CAN); 
        EnqueueStatement(sendStatement, senderState);
        
        MessagePacket duplicatePacket = CreateDuplicatePacketFrom(senderState, sendStatement);
        globalState.NetworkState().Buffer().add(duplicatePacket);
        
        ApplyRuleOn(globalState);

        GlobalRunTimeState expectedGlobalState = CreateDeadlockState();
        
        transitionCollectorChecker.ExpectTransition(CreateDeadlockTransition(), expectedGlobalState);
        
        VerifyEqualOutputForMultipleApply(SimpleStateInfo(globalState));
    }
    
    
    private MessagePacket CreateDuplicatePacketFrom(ActorState senderState, SendStatement sendStatement)
    {
        Actor arbitraryActor = CreateSofwareActor("arbitrary");

        
        return new MessagePacket(
                arbitraryActor, 
                arbitraryActor, 
                sendStatement.MessageLocator().Locate(senderState.Actor()), 
                sendStatement.Arguments());
    }
    
}
