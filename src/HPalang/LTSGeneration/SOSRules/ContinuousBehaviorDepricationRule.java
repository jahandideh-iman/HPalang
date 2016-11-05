/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.SOSRules;

import HPalang.LTSGeneration.RunTimeStates.ActorRunTimeState;
import HPalang.LTSGeneration.ConditionalLabel;
import HPalang.LTSGeneration.RunTimeStates.ContinuousBehavior;
import HPalang.LTSGeneration.RunTimeStates.GlobalRunTimeState;
import HPalang.LTSGeneration.LTSGenerator;
import HPalang.LTSGeneration.LTSGenerator;
import HPalang.LTSGeneration.Message;
import HPalang.LTSGeneration.MessageWithBody;
import HPalang.Statements.SendStatement;

/**
 *
 * @author Iman Jahandideh
 */
public class ContinuousBehaviorDepricationRule extends ActorLevelRule
{

    @Override
    protected boolean IsRuleSatisfied(ActorRunTimeState actorState, GlobalRunTimeState globalState)
    {
        return actorState.GetContinuousBehaviors().size() >0;
    }

    @Override
    protected void ApplyToActorState(ActorRunTimeState actorState, GlobalRunTimeState globalState, LTSGenerator generator)
    {
        for(ContinuousBehavior behavior : actorState.GetContinuousBehaviors())
        {
            GlobalRunTimeState newGlobalState = globalState.Clone();
            ActorRunTimeState newActorState = newGlobalState.FindActorState(actorState.GetActor());
            
            newActorState.RemoveContinuousBehavior(behavior);
            Message message = new MessageWithBody(behavior.GetActions());
            newActorState.EnqueueMessage(message);
            
            generator.AddTransition(new ConditionalLabel(behavior.GetGuard()), newGlobalState);
        }
    }   
}
