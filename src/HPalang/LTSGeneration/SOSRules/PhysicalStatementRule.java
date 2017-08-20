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
import HPalang.LTSGeneration.RunTimeStates.PhysicalActorState;
import HPalang.LTSGeneration.StateInfo;
import HPalang.LTSGeneration.TransitionCollector;

/**
 *
 * @author Iman Jahandideh
 */
public abstract class PhysicalStatementRule<T extends Statement> extends PhysicalActorLevelRule
{

    @Override
    protected boolean IsRuleSatisfied(PhysicalActorState actorState, StateInfo globalStateInfo)
    {
        
        return actorState.ExecutionQueueState().Statements().IsEmpty() == false
                && actorState.ExecutionQueueState().Statements().Head().Is(StatementType());
    }
    
    protected abstract Class<T> StatementType();

    @Override
    protected void ApplyToActorState(PhysicalActorState actorState, GlobalRunTimeState globalState, TransitionCollector collector)
    {
        GlobalRunTimeState newGlobalState = globalState.DeepCopy();
        PhysicalActorState newActorState = newGlobalState.ContinuousState().FindActorState(actorState.Actor());
        
        T statement = (T)newActorState.ExecutionQueueState().Statements().Dequeue();
        
        ApplyStatement(newActorState, statement);
        SoftwareLabel label = CreateTransitionLabel(actorState, statement);
        
        collector.AddTransition(label, newGlobalState);
    }
    
    protected abstract void ApplyStatement(PhysicalActorState actorState, T statement);
    protected SoftwareLabel CreateTransitionLabel(PhysicalActorState actorState, T statement)
    {
        return new SoftwareLabel();
    }
    
}
