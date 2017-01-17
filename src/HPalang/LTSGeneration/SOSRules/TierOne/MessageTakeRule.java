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
public abstract class MessageTakeRule extends ActorLevelRule
{
    protected final LTSGenerator tierTwoLTSGenerator;
    
    public MessageTakeRule(LTSGenerator tierTwoGenerator)
    {
        this.tierTwoLTSGenerator = tierTwoGenerator;
    }
    
    protected abstract boolean InternalIsRuleSatisfied(ActorRunTimeState actorState);
    protected abstract Message DequeuMessage(ActorRunTimeState actorState);
    
    @Override
    protected boolean IsRuleSatisfied(ActorRunTimeState actorState, GlobalRunTimeState globalState)
    {
        return InternalIsRuleSatisfied(actorState) == false 
                && actorState.StatementQueue().IsEmpty() == true
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
        
        newActorState.StatementQueue().Enqueue(DequeuMessage(newActorState).GetMessageBody());
        
        LabeledTransitionSystem lts = tierTwoLTSGenerator.Generate(newGlobalState);

        List<GlobalRunTimeState> outputs = FindState(lts,actorState.GetActor(), globalState);

        for(GlobalRunTimeState state : outputs)
            generator.AddTransition(new TauLabel(), state);
    }
    
    // TODO: Refactor this Crap!
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
