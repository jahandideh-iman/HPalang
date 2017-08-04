/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.SOSRules;

import HPalang.Core.Statement;
import HPalang.LTSGeneration.LTSGenerator;
import HPalang.LTSGeneration.RunTimeStates.ActorRunTimeState;
import HPalang.LTSGeneration.RunTimeStates.ExecutionQueueState;
import HPalang.LTSGeneration.RunTimeStates.GlobalRunTimeState;
import HPalang.LTSGeneration.Labels.SoftwareLabel;
import HPalang.LTSGeneration.RunTimeStates.PhysicalActorState;

/**
 *
 * @author Iman Jahandideh
 */
public abstract class PhysicalStatementRule<T extends Statement> extends PhysicalActorLevelRule
{

    @Override
    protected boolean IsRuleSatisfied(PhysicalActorState actorState, GlobalRunTimeState globalState)
    {
        
        return actorState.ExecutionQueueState().Statements().IsEmpty() == false
                && actorState.ExecutionQueueState().Statements().Head().Is(StatementType());
    }
    
    protected abstract Class<T> StatementType();

    @Override
    protected void ApplyToActorState(PhysicalActorState actorState, GlobalRunTimeState globalState, LTSGenerator generator)
    {
        GlobalRunTimeState newGlobalState = globalState.DeepCopy();
        PhysicalActorState newActorState = newGlobalState.ContinuousState().FindActorState(actorState.Actor());
        
        T statement = (T)newActorState.ExecutionQueueState().Statements().Dequeue();
        
        ApplyStatement(newActorState, statement);
        SoftwareLabel label = CreateTransitionLabel(actorState, statement);
        
        generator.AddTransition(label, newGlobalState);
    }
    
    protected abstract void ApplyStatement(PhysicalActorState actorState, T statement);
    protected SoftwareLabel CreateTransitionLabel(PhysicalActorState actorState, T statement)
    {
        return new SoftwareLabel();
    }
    
}
