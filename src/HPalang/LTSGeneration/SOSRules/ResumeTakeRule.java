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
import HPalang.LTSGeneration.TauLabel;
import HPalang.Statements.ResumeStatement;

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
        
        Message resumeMessage = FindResumeMessage(actorState);                                                                                     
        nextActorState.RemoveMessage(resumeMessage);
        nextActorState.SetDelayed(false);
        
        generator.AddTransition(new TauLabel(), nextGlobalState);
    }
    
    private boolean HasResumeMessage(ActorRunTimeState actorState)
    {
        for(Message message : actorState.GetMessages())
            if(message.GetMessageBody().contains(new ResumeStatement()))
                return true;
        return false;
    }

    private Message FindResumeMessage(ActorRunTimeState actorState)
    {
        for(Message message : actorState.GetMessages())
            if(message.GetMessageBody().contains(new ResumeStatement()))
                return message;
        return null;
    }  
}
