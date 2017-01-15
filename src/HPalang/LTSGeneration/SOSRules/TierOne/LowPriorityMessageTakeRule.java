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
import HPalang.LTSGeneration.SOSRules.ContinuousBehaviorRule;
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
public class LowPriorityMessageTakeRule extends ActorLevelRule
{

    @Override
    protected boolean IsRuleSatisfied(ActorRunTimeState actorState, GlobalRunTimeState globalState)
    {
        return actorState.IsSuspended() == false 
                && actorState.HighPriorityMessageQueue().IsEmpty() == true 
                && actorState.StatementQueue().IsEmpty() == true
                && actorState.LowPriorityMessageQueue().IsEmpty() == false
                && AllActorsHaveNoPendingStatement(globalState) == true;
    }
    
    private boolean AllActorsHaveNoPendingStatement(GlobalRunTimeState globalState)
    {
        for(ActorRunTimeState actorState : globalState.GetActorStates())
            if(actorState.StatementQueue().IsEmpty() == false)
                return false;
        return true;
    }

    @Override
    protected void ApplyToActorState(ActorRunTimeState actorState, GlobalRunTimeState globalState, LTSGenerator generator)
    {
        GlobalRunTimeState newGlobalState = globalState.DeepCopy();
        
        ActorRunTimeState newActorState = newGlobalState.FindActorState(actorState.GetActor());
        
        Message message = newActorState.LowPriorityMessageQueue().Head();
        newActorState.LowPriorityMessageQueue().Dequeue();
        newActorState.StatementQueue().Enqueue(message.GetMessageBody());
        
        LTSGenerator ltsGenerator = new LTSGenerator();
        ltsGenerator.AddSOSRule(new ContinuousBehaviorRule());
        ltsGenerator.AddSOSRule(new MessageSendRule());
        ltsGenerator.AddSOSRule(new MessageDropRule());
        ltsGenerator.AddSOSRule(new DelayStatementRule());

        LabeledTransitionSystem lts = ltsGenerator.Generate(newGlobalState);

        List<GlobalRunTimeState> outputs = FindState(lts,actorState.GetActor(), globalState);

        for(GlobalRunTimeState state : outputs)
            generator.AddTransition(new TauLabel(), state);
    }
    
    private List<GlobalRunTimeState> FindState(LabeledTransitionSystem lts, Actor actor, GlobalRunTimeState rootGlobalState)
    {
        List<GlobalRunTimeState> states = new LinkedList<>();
        
        for(GlobalRunTimeState state : lts.GetStates())
        {
            boolean valid = true;
            for(ActorRunTimeState actorState : state.GetActorStates())
            {
                if(actorState.GetActor() == actor)
                {
                    if(actorState.StatementQueue().IsEmpty() == false)
                        valid = false;
                }
                else
                {
                    ActorRunTimeState rootActorState = rootGlobalState.FindActorState(actorState.GetActor());
                    if(actorState.StatementQueue().IsEmpty() == false || rootActorState.ValuationEqual(actorState) == false)
                        valid = false;
                }
                    
            }
            
            if(valid)
                states.add(state);
        }
        
        return states;
    }
}
