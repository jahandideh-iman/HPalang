/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.SOSRules.MessageSendRules;

import HPalang.Core.Actor;
import static HPalang.Core.CommunicationType.Wire;
import HPalang.Core.MessagePacket;
import HPalang.Core.Statements.SendStatement;
import HPalang.LTSGeneration.Labels.SoftwareLabel;
import HPalang.LTSGeneration.RunTimeStates.GlobalRunTimeState;
import HPalang.LTSGeneration.SOSRules.MessageSendRuleTestFixture;
import static TestUtilities.CoreUtility.*;
import static TestUtilities.NetworkingUtility.*;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 *
 * @author Iman Jahandideh
 */
public class WireMessageSendRuleTest extends MessageSendRuleTestFixture
{
    @Before
    public void Setup()
    {
        SetupSenderAndReceiver();
        rule = new WireMessageSendRule();  
        communicationType = Wire;
    }
    
    @Override
    protected MessagePacket FindSentLastPacket(Actor actor, GlobalRunTimeState globalState)
    {
        return FindLastPacketInMessageQueue(actor, globalState);
    }
    
    @Test
    public void SendsMessagePacketToReceiverIfCommunicationTypeIsWired()
    {  
        SendStatement sendStatement = CreateEmptySendStatementTo(receiver);
        
        senderState.Actor().SetCommunicationType(receiver, Wire); 
        EnqueueStatement(sendStatement, senderState);
        
        ApplyRuleOn(globalState);

        GlobalRunTimeState expectedGlobalState = globalState.DeepCopy();
        MessagePacket expectedPacket = MessagePacket(senderState, sendStatement);
        ClearStatementsFor(FindActorState(sender, expectedGlobalState));
        PutMessagePacketInActor(expectedPacket, FindActorState(receiver, expectedGlobalState));
        
        transitionCollectorChecker.ExpectTransition(new SoftwareLabel(), expectedGlobalState);
        VerifyEqualOutputForMultipleApply(SimpleStateInfo(globalState));
    } 
    
    @Test
    public void DoesNotSendTheMessageIfTheRecieverMessageQueueIsFull()
    {
        SendStatement sendStatement = CreateEmptySendStatementTo(receiver);

        senderState.SActor().SetCommunicationType(receiver, Wire);
        EnqueueStatement(sendStatement, senderState);
        FillActorsQeueue(receiverState);

        ApplyRuleOn(globalState);

        transitionCollectorChecker.ExpectNoTransition();
        VerifyEqualOutputForMultipleApply(SimpleStateInfo(globalState));
    }


}
