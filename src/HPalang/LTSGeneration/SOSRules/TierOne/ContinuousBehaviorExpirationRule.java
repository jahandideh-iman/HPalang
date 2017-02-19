/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.SOSRules.TierOne;

import HPalang.LTSGeneration.RunTimeStates.ActorRunTimeState;
import HPalang.LTSGeneration.GuardedlLabel;
import HPalang.LTSGeneration.RunTimeStates.ContinuousBehavior;
import HPalang.LTSGeneration.GlobalRunTimeState;
import HPalang.LTSGeneration.LTSGenerator;
import HPalang.Core.Message;
import HPalang.Core.Messages.MessageWithBody;
import HPalang.LTSGeneration.SOSRules.ActorLevelRule;

/**
 *
 * @author Iman Jahandideh
 */
public class ContinuousBehaviorExpirationRule extends ActorLevelRule
{

    @Override
    protected boolean IsRuleSatisfied(ActorRunTimeState actorState, GlobalRunTimeState globalState)
    {
        return actorState.ContinuousBehaviors().Size() >0;
    }

    @Override
    protected void ApplyToActorState(ActorRunTimeState actorState, GlobalRunTimeState globalState, LTSGenerator generator)
    {
        for(ContinuousBehavior behavior : actorState.ContinuousBehaviors())
        {
            GlobalRunTimeState newGlobalState = globalState.DeepCopy();
            ActorRunTimeState newActorState = newGlobalState.FindActorState(actorState.GetActor());
            
            newActorState.ContinuousBehaviors().Remove(behavior);
            Message message = new MessageWithBody(behavior.GetActions());
            newActorState.HighPriorityMessageQueue().Enqueue(message);
            
            generator.AddTransition(new GuardedlLabel(behavior.GetGuard()), newGlobalState);
        }
    }   
}
