/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.SOSRules;

import HPalang.Core.Statement;
import HPalang.LTSGeneration.LTSGenerator;
import HPalang.LTSGeneration.RunTimeStates.ActorRunTimeState;
import HPalang.LTSGeneration.RunTimeStates.GlobalRunTimeState;
import HPalang.LTSGeneration.TauLabel;

/**
 *
 * @author Iman Jahandideh
 */
public abstract class StatementRule<T extends Statement> extends ActorLevelRule
{

    @Override
    protected boolean IsRuleSatisfied(ActorRunTimeState actorState, GlobalRunTimeState globalState)
    {
        
        return actorState.StatementQueue().IsEmpty() == false
                && actorState.StatementQueue().Head().Is(StatementType());
    }
    
    protected abstract Class<T> StatementType();

    @Override
    protected void ApplyToActorState(ActorRunTimeState actorState, GlobalRunTimeState globalState, LTSGenerator generator)
    {
        GlobalRunTimeState newGlobalState = globalState.DeepCopy();
        ActorRunTimeState newActorState = newGlobalState.FindActorState(actorState.GetActor());
        
        T statement = (T)newActorState.StatementQueue().Head();
        newActorState.StatementQueue().Dequeue();
        ApplyStatement(newActorState, statement);
        
        TauLabel label = CreateTransitionLabel(actorState, statement);
        
        generator.AddTransition(label, newGlobalState);
    }
    
    protected abstract void ApplyStatement(ActorRunTimeState actorState, T statement);
    protected TauLabel CreateTransitionLabel(ActorRunTimeState actorState, T statement)
    {
        return new TauLabel();
    }
    
}
