/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.SOSRules;

import HPalang.Core.Message;
import HPalang.LTSGeneration.LTSGenerator;
import HPalang.LTSGeneration.Labels.SoftwareLabel;
import HPalang.LTSGeneration.RunTimeStates.ActorRunTimeState;
import HPalang.LTSGeneration.RunTimeStates.GlobalRunTimeState;

/**
 *
 * @author Iman Jahandideh
 */
public class FIFOMessageTakeRule extends ActorLevelRule
{

    @Override
    protected boolean IsRuleSatisfied(ActorRunTimeState actorState, GlobalRunTimeState globalState)
    {
        return  actorState.ExecutionQueueState().Statements().IsEmpty() &&
                !actorState.MessageQueueState().Messages().IsEmpty() &&
                !actorState.IsSuspended();
    }

    @Override
    protected void ApplyToActorState(ActorRunTimeState actorState, GlobalRunTimeState globalState, LTSGenerator generator)
    {
        GlobalRunTimeState newGlobalState = globalState.DeepCopy();
        
        ActorRunTimeState newActorState = newGlobalState.FindActorState(actorState.GetActor());
        
        Message message = newActorState.MessageQueueState().Messages().Dequeue();
        newActorState.ExecutionQueueState().Statements().Enqueue(message.GetMessageBody());
        
        generator.AddTransition(new SoftwareLabel(), newGlobalState);
    }
}
