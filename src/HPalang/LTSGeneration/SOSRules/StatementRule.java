/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.SOSRules;

import HPalang.Core.Statement;
import HPalang.LTSGeneration.Labels.SoftwareLabel;
import HPalang.LTSGeneration.RunTimeStates.ActorState;
import HPalang.LTSGeneration.RunTimeStates.GlobalRunTimeState;
import HPalang.LTSGeneration.RunTimeStates.SoftwareActorState;
import HPalang.LTSGeneration.TransitionCollector;

/**
 *
 * @author Iman Jahandideh
 */
public abstract class StatementRule<T extends Statement> extends ActorLevelRule
{
    @Override
    protected final boolean IsRuleSatisfied(ActorState actorState, GlobalRunTimeState globalState)
    {
        
        return actorState.ExecutionQueueState().Statements().IsEmpty() == false &&
                actorState.ExecutionQueueState().Statements().Head().Is(StatementType()) &&
                InternalIsRuleSatisfied(actorState);
    }
    
    protected boolean InternalIsRuleSatisfied(ActorState actorState)
    {
        return true;
    }
    
    protected abstract Class<T> StatementType();

    @Override
    protected final void ApplyToActorState(ActorState actorState, GlobalRunTimeState globalState, TransitionCollector collector)
    {
        GlobalRunTimeState newGlobalState = globalState.DeepCopy();
        
        ActorState newActorState = newGlobalState.FindActorState(actorState.Actor());
        
        T statement = (T)newActorState.ExecutionQueueState().Statements().Head();
        newActorState.ExecutionQueueState().Statements().Dequeue();
        ApplyStatement(newActorState, statement);
        
        SoftwareLabel label = CreateTransitionLabel(newActorState, statement);
        
        collector.AddTransition(label, newGlobalState);
    }
    
    protected abstract void ApplyStatement(ActorState newActorState, T statement);
    protected SoftwareLabel CreateTransitionLabel(ActorState actorState, T statement)
    {
        return new SoftwareLabel();
    }
    
}
