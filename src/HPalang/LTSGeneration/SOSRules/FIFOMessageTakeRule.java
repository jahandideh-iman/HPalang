/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.SOSRules;

import HPalang.Core.Message;
import HPalang.LTSGeneration.LTSGenerator;
import HPalang.LTSGeneration.Labels.SoftwareLabel;
import HPalang.LTSGeneration.RunTimeStates.SoftwareActorState;
import HPalang.LTSGeneration.RunTimeStates.GlobalRunTimeState;
import HPalang.LTSGeneration.TransitionCollector;

/**
 *
 * @author Iman Jahandideh
 */
public class FIFOMessageTakeRule extends ActorLevelRule
{

    @Override
    protected boolean IsRuleSatisfied(SoftwareActorState actorState, GlobalRunTimeState globalState)
    {
        return  actorState.ExecutionQueueState().Statements().IsEmpty() &&
                !actorState.MessageQueueState().Messages().IsEmpty() &&
                !actorState.IsSuspended();
    }

    @Override
    protected void ApplyToActorState(SoftwareActorState actorState, GlobalRunTimeState globalState, TransitionCollector collector)
    {
        GlobalRunTimeState newGlobalState = globalState.DeepCopy();
        
        SoftwareActorState newActorState = newGlobalState.DiscreteState().FindActorState(actorState.Actor());
        
        Message message = newActorState.MessageQueueState().Messages().Dequeue();
        newActorState.ExecutionQueueState().Statements().Enqueue(message.GetMessageBody());
        
        collector.AddTransition(new SoftwareLabel(), newGlobalState);
    }
}
