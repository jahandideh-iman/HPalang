/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.SOSRules.MessageSendRules;

import HPalang.Core.CommunicationType;
import HPalang.Core.Message;
import HPalang.Core.MessagePacket;
import HPalang.Core.Statements.SendStatement;
import HPalang.LTSGeneration.RunTimeStates.ActorState;
import HPalang.LTSGeneration.RunTimeStates.GlobalRunTimeState;
import HPalang.LTSGeneration.RunTimeStates.MessageQueueState;
import HPalang.LTSGeneration.SOSRules.MessageSendRule;

/**
 *
 * @author Iman Jahandideh
 */
public class WireMessageSendRule extends MessageSendRule
{

    @Override
    protected boolean InternalIsRuleSatisfied(GlobalRunTimeState globalState, SendStatement sendStatement, Message message, ActorState receiverState)
    {
        MessageQueueState queueState = receiverState.MessageQueueState();

        return queueState.Messages().Size() < receiverState.Actor().QueueCapacity();
    }

    @Override
    protected CommunicationType RuleCommunicationType()
    {
        return CommunicationType.Wire;
    }

    @Override
    protected void InternalApply(GlobalRunTimeState newGlobalState, MessageQueueState newRecieverMessageQueueState, MessagePacket packet)
    {
        newRecieverMessageQueueState.Messages().Enqueue(packet);
    }
    
    @Override
    protected boolean InternalMustGoToDeadlock(GlobalRunTimeState globalState, MessagePacket packet)
    {
        return false;
    }

    
}
