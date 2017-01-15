/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.SOSRules;

import HPalang.Core.Actor;
import HPalang.LTSGeneration.RunTimeStates.ActorRunTimeState;
import HPalang.LTSGeneration.RunTimeStates.ContinuousBehavior;
import HPalang.LTSGeneration.RunTimeStates.GlobalRunTimeState;
import HPalang.LTSGeneration.LTSGenerator;
import HPalang.LTSGeneration.TauLabel;
import HPalang.Core.Statements.DelayStatement;
import HPalang.Core.Statements.ResumeStatement;
import HPalang.Core.Statement;

/**
 *
 * @author Iman Jahandideh
 */
public class DelayStatementRule extends ActorLevelRule
{

    @Override
    protected boolean IsRuleSatisfied(ActorRunTimeState actorState, GlobalRunTimeState globalState)
    {
        return actorState.StatementQueue().Head() instanceof DelayStatement && actorState.IsSuspended() == false;
    }

    @Override
    protected void ApplyToActorState(ActorRunTimeState actorState, GlobalRunTimeState globalState, LTSGenerator generator)
    {
        GlobalRunTimeState nextGlobalState = globalState.DeepCopy();
        ActorRunTimeState nextActorState = nextGlobalState.FindActorState(actorState.GetActor());
        
        DelayStatement delayStatement = (DelayStatement)actorState.StatementQueue().Head();
     
        String delayVar = actorState.GetActor().GetDelayVariableName();
        ContinuousBehavior behavior = CreateDelayBehavior(actorState.GetActor(), delayStatement.GetDelay(), delayVar);
        nextActorState.SetSuspended(true);
        nextActorState.StatementQueue().Dequeue();
        nextActorState.ContinuousBehaviors().Add(behavior);
        
        generator.AddTransition(new TauLabel(),nextGlobalState);
    }
    
    private ContinuousBehavior CreateDelayBehavior(Actor actor,float delay, String delayVar)
    {
        return new ContinuousBehavior(delayVar+"<="+delay,delayVar+"'=1",delayVar+"=="+delay,Statement.StatementsFrom(new ResumeStatement()));
    }
}
