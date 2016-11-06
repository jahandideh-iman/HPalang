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

/**
 *
 * @author Iman Jahandideh
 */
public class MessageTakeRule extends ActorLevelRule
{

    @Override
    protected boolean IsRuleSatisfied(ActorRunTimeState actorState, GlobalRunTimeState globalState)
    {
        return actorState.IsDelayed() == false && actorState.HasMessage() && actorState.HasStatement() == false;
    }

    @Override
    protected void ApplyToActorState(ActorRunTimeState actorState, GlobalRunTimeState globalState, LTSGenerator generator)
    {
        GlobalRunTimeState newGlobalState = globalState.DeepCopy();
        
        ActorRunTimeState newActorState = newGlobalState.FindActorState(actorState.GetActor());
        
        Message message = newActorState.GetNextMessage();
        newActorState.DequeueNextMessage();
        
        newActorState.EnqueueStatements(message.GetMessageBody());
        
        generator.AddTransition(new TauLabel(), newGlobalState);
    }
  
}
