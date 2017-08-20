/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.SOSRules;

import HPalang.Core.Statement;
import HPalang.LTSGeneration.LTSGenerator;
import HPalang.LTSGeneration.RunTimeStates.SoftwareActorState;
import HPalang.LTSGeneration.RunTimeStates.ExecutionQueueState;
import HPalang.LTSGeneration.RunTimeStates.GlobalRunTimeState;
import HPalang.LTSGeneration.Labels.SoftwareLabel;
import HPalang.LTSGeneration.TransitionCollector;

/**
 *
 * @author Iman Jahandideh
 */
public abstract class StatementRule<T extends Statement> extends ActorLevelRule
{

    @Override
    protected boolean IsRuleSatisfied(SoftwareActorState actorState, GlobalRunTimeState globalState)
    {
        
        return actorState.FindSubState(ExecutionQueueState.class).Statements().IsEmpty() == false
                && actorState.FindSubState(ExecutionQueueState.class).Statements().Head().Is(StatementType());
    }
    
    protected abstract Class<T> StatementType();

    @Override
    protected void ApplyToActorState(SoftwareActorState actorState, GlobalRunTimeState globalState, TransitionCollector collector)
    {
        GlobalRunTimeState newGlobalState = globalState.DeepCopy();
        SoftwareActorState newActorState = newGlobalState.DiscreteState().FindActorState(actorState.Actor());
        
        T statement = (T)newActorState.FindSubState(ExecutionQueueState.class).Statements().Head();
        newActorState.FindSubState(ExecutionQueueState.class).Statements().Dequeue();
        ApplyStatement(newActorState, statement);
        
        SoftwareLabel label = CreateTransitionLabel(actorState, statement);
        
        collector.AddTransition(label, newGlobalState);
    }
    
    protected abstract void ApplyStatement(SoftwareActorState actorState, T statement);
    protected SoftwareLabel CreateTransitionLabel(SoftwareActorState actorState, T statement)
    {
        return new SoftwareLabel();
    }
    
}
