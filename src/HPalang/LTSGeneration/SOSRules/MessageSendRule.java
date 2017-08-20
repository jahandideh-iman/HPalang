/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.SOSRules;

import HPalang.Core.Statements.DelayStatement;
import HPalang.LTSGeneration.LTSGenerator;
import HPalang.LTSGeneration.RunTimeStates.SoftwareActorState;
import HPalang.LTSGeneration.RunTimeStates.GlobalRunTimeState;
import HPalang.LTSGeneration.Labels.SoftwareLabel;
import HPalang.Core.Statements.SendStatement;
import HPalang.LTSGeneration.RunTimeStates.ExecutionQueueState;
import HPalang.LTSGeneration.RunTimeStates.MessageQueueState;
import HPalang.LTSGeneration.TransitionCollector;

/**
 *
 * @author Iman Jahandideh
 */
public class MessageSendRule extends ActorLevelRule
{
    @Override
    protected boolean IsRuleSatisfied(SoftwareActorState actorState, GlobalRunTimeState globalState)
    {
        if((actorState.FindSubState(ExecutionQueueState.class).Statements().Head() instanceof SendStatement) == false)
            return false;
        
        SendStatement sendStatement = (SendStatement)actorState.FindSubState(ExecutionQueueState.class).Statements().Head();
        SoftwareActorState receiverState = globalState.DiscreteState().FindActorState(sendStatement.GetReceiver());
        
        MessageQueueState queueState = receiverState.FindSubState(MessageQueueState.class);
        return  queueState.Messages().Size() < receiverState.GetMessageQueueCapacity();
    }

    @Override
    protected void ApplyToActorState(SoftwareActorState actorState, GlobalRunTimeState globalState, TransitionCollector collector)
    {
        GlobalRunTimeState newGlobalState = globalState.DeepCopy();

        SendStatement sendStatement = (SendStatement)actorState.FindSubState(ExecutionQueueState.class).Statements().Head();
        
        SoftwareActorState senderState = newGlobalState.DiscreteState().FindActorState(actorState.Actor());
        SoftwareActorState receiverState = newGlobalState.DiscreteState().FindActorState(sendStatement.GetReceiver());
        MessageQueueState reciverMessageQueueState = receiverState.FindSubState(MessageQueueState.class);
        senderState.ExecutionQueueState().Statements().Dequeue();
        
        reciverMessageQueueState.Messages().Enqueue(sendStatement.GetMessage());
        
        collector.AddTransition(new SoftwareLabel(), newGlobalState);
    }
}
