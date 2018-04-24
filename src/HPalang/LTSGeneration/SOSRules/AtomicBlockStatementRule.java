/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.SOSRules;

import HPalang.Core.Actor;
import HPalang.Core.Statements.AtomicBlockStatement;
import HPalang.LTSGeneration.RunTimeStates.ActorState;
import HPalang.LTSGeneration.RunTimeStates.GlobalRunTimeState;
import HPalang.LTSGeneration.TransitionCollector;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Iman Jahandideh
 */
public class AtomicBlockStatementRule extends ActorLevelRule
{
    
    private final List<ActorLevelRule> rules;
    
    public AtomicBlockStatementRule(List<ActorLevelRule> rules)
    {
        this.rules = new ArrayList<>(rules);
    }

    @Override
    protected boolean IsRuleSatisfied(ActorState actorState, GlobalRunTimeState globalState)
    {
        return actorState.IsSuspended() == false &&
                actorState.ExecutionQueueState().Statements().IsEmpty() == false
                && actorState.ExecutionQueueState().Statements().Head().Is(AtomicBlockStatement.class);
    }

    @Override
    protected void ApplyToActorState(ActorState actorState, GlobalRunTimeState globalState, TransitionCollector collector)
    {
        TransitionCollector internalCollector = null;
        GlobalRunTimeState newGlobalState = globalState.DeepCopy();
        ActorState newActorState = newGlobalState.FindActorState(actorState.Actor());
        Actor actor = actorState.Actor();
        
        for(ActorLevelRule rule : rules)
        {
            rule.ApplyToActorState(actorState, globalState, collector);
        }
    }


}
