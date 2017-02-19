/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.SOSRules.TierOne;

import HPalang.Core.Message;
import HPalang.LTSGeneration.LTSGenerator;
import HPalang.LTSGeneration.RunTimeStates.ActorRunTimeState;

/**
 *
 * @author Iman Jahandideh
 */
public class HighPriorityMessageTakeRule extends MessageTakeRule
{

    public HighPriorityMessageTakeRule(LTSGenerator tierTwoGenerator)
    {
        super(tierTwoGenerator);
    }

    @Override
    protected boolean InternalIsRuleSatisfied(ActorRunTimeState actorState)
    {
        return actorState.HighPriorityMessageQueue().IsEmpty() == false;
    }

    @Override
    protected Message DequeuMessage(ActorRunTimeState actorState)
    {
        Message message = actorState.HighPriorityMessageQueue().Head();
        actorState.HighPriorityMessageQueue().Dequeue();
        return message;
    }

}
