/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.SOSRules;

import HPalang.Core.Actor;
import HPalang.LTSGeneration.RunTimeStates.ActorRunTimeState;
import HPalang.LTSGeneration.ContinuousBehavior;
import HPalang.LTSGeneration.RunTimeStates.GlobalRunTimeState;
import HPalang.LTSGeneration.LTSGenerator;
import HPalang.LTSGeneration.ResumeMessage;
import HPalang.LTSGeneration.TauLabel;
import HPalang.Statements.DelayStatement;
import HPalang.Statements.SendStatement;
import HPalang.Statements.Statement;
import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author Iman Jahandideh
 */
public class DelayRule extends ActorLevelRule
{

    @Override
    protected boolean IsRuleSatisfied(ActorRunTimeState actorState, GlobalRunTimeState globalState)
    {
        return actorState.GetNextStatement() instanceof DelayStatement && actorState.IsDelayed() == false;
    }

    @Override
    protected void ApplyToActorState(ActorRunTimeState actorState, GlobalRunTimeState globalState, LTSGenerator generator)
    {
        DelayStatement delayStatement = (DelayStatement)actorState.GetNextStatement();
        ActorRunTimeState newActorState = globalState.FindActorState(actorState.GetActor());
        
        String delayVar = actorState.GetActor().GetName()+"_Delay";
        ContinuousBehavior behavior = CreateDelayBehavior(actorState.GetActor(), delayStatement.GetDelay(), delayVar);
        newActorState.SetDelayed(true);
        newActorState.DequeueNextStatement();
        newActorState.AddContinuousBehavior(behavior);
        
        generator.AddTransition(new TauLabel(),globalState);
    }
    
    private ContinuousBehavior CreateDelayBehavior(Actor actor,float delay, String delayVar)
    {
        Queue<Statement> statements = new LinkedList<Statement>();
        statements.add(new SendStatement(actor, new ResumeMessage()));
        return new ContinuousBehavior(delayVar+"<="+delay,delayVar+"'=1",delayVar+"=="+delay,statements);
    }

    @Override
    public void TryApply(GlobalRunTimeState globalState, LTSGenerator generator)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
