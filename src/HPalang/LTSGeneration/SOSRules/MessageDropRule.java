/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.SOSRules;

import HPalang.LTSGeneration.LTSGenerator;
import HPalang.LTSGeneration.RunTimeStates.ActorRunTimeState;
import HPalang.LTSGeneration.RunTimeStates.GlobalRunTimeState;
import HPalang.LTSGeneration.TauLabel;
import HPalang.Core.Statements.SendStatement;

/**
 *
 * @author Iman Jahandideh
 */
public class MessageDropRule extends ActorLevelRule
{
     @Override
    protected boolean IsRuleSatisfied(ActorRunTimeState actorState, GlobalRunTimeState globalState)
    {
        if((actorState.GetNextStatement() instanceof SendStatement) == false || actorState.IsDelayed())
            return false;
        
        SendStatement sendStatement = (SendStatement)actorState.GetNextStatement();
        ActorRunTimeState receiverState = globalState.FindActorState(sendStatement.GetReceiver());
        
        return  receiverState.GetMessageQueueSize() >= receiverState.GetMessageQueueCapacity();
    }

    @Override
    protected void ApplyToActorState(ActorRunTimeState actorState, GlobalRunTimeState globalState, LTSGenerator generator)
    {
        GlobalRunTimeState newGlobalState = globalState.DeepCopy();
               
        ActorRunTimeState senderState = newGlobalState.FindActorState(actorState.GetActor());
        
        senderState.DequeueNextStatement();
        
        generator.AddTransition(new TauLabel(), newGlobalState);
    }
}
