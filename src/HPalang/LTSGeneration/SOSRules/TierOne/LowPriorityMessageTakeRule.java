/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.SOSRules.TierOne;

import HPalang.Core.Actor;
import HPalang.Core.Message;
import HPalang.LTSGeneration.LTSGenerator;
import HPalang.LTSGeneration.LabeledTransitionSystem;
import HPalang.LTSGeneration.RunTimeStates.ActorRunTimeState;
import HPalang.LTSGeneration.RunTimeStates.GlobalRunTimeState;
import HPalang.LTSGeneration.SOSRules.ActorLevelRule;
import HPalang.LTSGeneration.SOSRules.ContinuousBehaviorStatementRule;
import HPalang.LTSGeneration.SOSRules.DelayStatementRule;
import HPalang.LTSGeneration.SOSRules.MessageDropRule;
import HPalang.LTSGeneration.SOSRules.MessageSendRule;
import HPalang.LTSGeneration.TauLabel;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Iman Jahandideh
 */
public class LowPriorityMessageTakeRule extends MessageTakeRule
{

    public LowPriorityMessageTakeRule(LTSGenerator tierTwoGenerator)
    {
        super(tierTwoGenerator);
    }

    @Override
    protected boolean InternalIsRuleSatisfied(ActorRunTimeState actorState)
    {
        return actorState.LowPriorityMessageQueue().IsEmpty() && actorState.IsSuspended() == false;
    }

    @Override
    protected Message DequeuMessage(ActorRunTimeState actorState)
    {
        Message message = actorState.LowPriorityMessageQueue().Head();
        actorState.LowPriorityMessageQueue().Dequeue();
        return message;
    }
}
