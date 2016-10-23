/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.SOSRules;

import HPalang.LTSGeneration.RunTimeStates.ActorRunTimeState;
import HPalang.LTSGeneration.RunTimeStates.GlobalRunTimeState;
import HPalang.LTSGeneration.LTSGenerator;
import HPalang.LTSGeneration.LTSGenerator;
import HPalang.LTSGeneration.Message;
import HPalang.LTSGeneration.ResumeMessage;
import HPalang.LTSGeneration.TauLabel;

/**
 *
 * @author Iman Jahandideh
 */
public class ResumeTakeRule extends ActorLevelRule
{

    @Override
    protected boolean IsRuleSatisfied(ActorRunTimeState actorState, GlobalRunTimeState globalState)
    {
        return actorState.IsDelayed() && HasResumeMessage(actorState);
    }

    @Override
    protected void ApplyToActorState(ActorRunTimeState actorState, GlobalRunTimeState globalState, LTSGenerator generator)
    {
        GlobalRunTimeState nextGlobalState = globalState.Clone();
        ActorRunTimeState nextActorState = nextGlobalState.FindActorState(actorState.GetActor());
        
        ResumeMessage resumeMessage = FindResumeMessage(actorState);                                                                                     
        nextActorState.RemoveMessage(resumeMessage);
        nextActorState.SetDelayed(false);
        
        generator.AddTransition(new TauLabel(), nextGlobalState);
    }
    
    private boolean HasResumeMessage(ActorRunTimeState actorState)
    {
        for(Message message : actorState.GetMessages())
            if(message instanceof ResumeMessage)
                return true;
        return false;
    }

    private ResumeMessage FindResumeMessage(ActorRunTimeState actorState)
    {
        for(Message message : actorState.GetMessages())
            if(message instanceof ResumeMessage)
                return (ResumeMessage)message;
        return null;
    }

    @Override
    public void TryApply(GlobalRunTimeState globalState, LTSGenerator generator)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
