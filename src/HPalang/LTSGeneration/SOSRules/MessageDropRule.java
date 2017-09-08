/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.SOSRules;

import HPalang.Core.SoftwareActor;
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
public class MessageDropRule extends SoftwareActorLevelRule
{
     @Override
    protected boolean IsRuleSatisfied(SoftwareActorState actorState, GlobalRunTimeState globalState)
    {
        if((actorState.FindSubState(ExecutionQueueState.class).Statements().Head() instanceof SendStatement) == false)
            return false;
        
        SendStatement sendStatement = (SendStatement)actorState.FindSubState(ExecutionQueueState.class).Statements().Head();
        
        SoftwareActor receiver = (SoftwareActor) sendStatement.ReceiverLocator().GetActor();   
        SoftwareActorState receiverState = globalState.DiscreteState().FindActorState(receiver);
        
        MessageQueueState queueState = receiverState.FindSubState(MessageQueueState.class);
        
        return  queueState.Messages().Size() >= receiverState.GetMessageQueueCapacity();
    }

    @Override
    protected void ApplyToActorState(SoftwareActorState actorState, GlobalRunTimeState globalState, TransitionCollector collector)
    {
        GlobalRunTimeState newGlobalState = globalState.DeepCopy();
               
        SoftwareActorState senderState = newGlobalState.DiscreteState().FindActorState(actorState.SActor());
        
        senderState.FindSubState(ExecutionQueueState.class).Statements().Dequeue();
        
        collector.AddTransition(new SoftwareLabel(), newGlobalState);
    }
}
