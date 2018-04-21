/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.SOSRules;

import HPalang.Core.Statement;
import HPalang.LTSGeneration.RunTimeStates.SoftwareActorState;
import HPalang.LTSGeneration.RunTimeStates.GlobalRunTimeState;
import HPalang.LTSGeneration.Labels.SoftwareLabel;
import HPalang.LTSGeneration.TransitionCollector;
import HPalang.LTSGeneration.Utilities.CreationUtility;

/**
 *
 * @author Iman Jahandideh
 */
public abstract class SoftwareStatementRule<T extends Statement> extends SoftwareActorLevelRule
{

    @Override
    protected final boolean IsRuleSatisfied(SoftwareActorState actorState, GlobalRunTimeState globalState)
    {
        return  actorState.IsSuspended() == false &&
                actorState.ExecutionQueueState().Statements().IsEmpty() == false &&
                actorState.ExecutionQueueState().Statements().Head().Is(StatementType()) &&
                InternalIsRuleSatisfied(actorState);
    }
    
    protected boolean InternalIsRuleSatisfied(SoftwareActorState actorState)
    {
        return true;
    }
    
    protected abstract Class<T> StatementType();

    @Override
    protected final void ApplyToActorState(SoftwareActorState actorState, GlobalRunTimeState globalState, TransitionCollector collector)
    {
        GlobalRunTimeState newGlobalState = globalState.DeepCopy();
        SoftwareActorState newActorState = newGlobalState.DiscreteState().FindActorState(actorState.SActor());
        
        T statement = (T)newActorState.ExecutionQueueState().Statements().Dequeue();
        
        if(MustGoToDeadlock(globalState, statement))
            collector.AddTransition(CreationUtility.CreateDeadlockTransition(), CreationUtility.CreateDeadlockState());
        else
        {
            ApplyStatement(newActorState, statement, newGlobalState);
            SoftwareLabel label = CreateTransitionLabel(actorState.DeepCopy(), statement, globalState.DeepCopy());
            collector.AddTransition(label, newGlobalState);
        }
    }
    
    protected boolean MustGoToDeadlock(GlobalRunTimeState globalState, T statement)
    {
        return false;
    }
    
    protected abstract void ApplyStatement(SoftwareActorState newActorState, T statement, GlobalRunTimeState newGlobalState);
    protected SoftwareLabel CreateTransitionLabel(SoftwareActorState actorState, T statement, GlobalRunTimeState globalState)
    {
        return new SoftwareLabel();
    }
    
}
