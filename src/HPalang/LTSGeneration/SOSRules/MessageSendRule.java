/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.SOSRules;

import HPalang.Core.Statements.DelayStatement;
import HPalang.LTSGeneration.LTSGenerator;
import HPalang.LTSGeneration.RunTimeStates.ActorRunTimeState;
import HPalang.LTSGeneration.RunTimeStates.GlobalRunTimeState;
import HPalang.LTSGeneration.Labels.SoftwareLabel;
import HPalang.Core.Statements.SendStatement;
import HPalang.LTSGeneration.RunTimeStates.ExecutionQueueState;
import HPalang.LTSGeneration.RunTimeStates.MessageQueueState;

/**
 *
 * @author Iman Jahandideh
 */
public class MessageSendRule extends ActorLevelRule
{
    @Override
    protected boolean IsRuleSatisfied(ActorRunTimeState actorState, GlobalRunTimeState globalState)
    {
        if((actorState.FindSubState(ExecutionQueueState.class).Statements().Head() instanceof SendStatement) == false)
            return false;
        
        SendStatement sendStatement = (SendStatement)actorState.FindSubState(ExecutionQueueState.class).Statements().Head();
        ActorRunTimeState receiverState = globalState.FindActorState(sendStatement.GetReceiver());
        
        MessageQueueState queueState = receiverState.FindSubState(MessageQueueState.class);
        return  queueState.Messages().Size() < receiverState.GetMessageQueueCapacity();
    }

    @Override
    protected void ApplyToActorState(ActorRunTimeState actorState, GlobalRunTimeState globalState, LTSGenerator generator)
    {
        GlobalRunTimeState newGlobalState = globalState.DeepCopy();

        SendStatement sendStatement = (SendStatement)actorState.FindSubState(ExecutionQueueState.class).Statements().Head();
        
        ActorRunTimeState senderState = newGlobalState.FindActorState(actorState.GetActor());
        ActorRunTimeState receiverState = newGlobalState.FindActorState(sendStatement.GetReceiver());
        MessageQueueState reciverMessageQueueState = receiverState.FindSubState(MessageQueueState.class);
        senderState.FindSubState(ExecutionQueueState.class).Statements().Dequeue();
        
        reciverMessageQueueState.Messages().Enqueue(sendStatement.GetMessage());
        
        generator.AddTransition(new SoftwareLabel(), newGlobalState);
    }
}
