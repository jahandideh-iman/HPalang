/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.SOSRules;

import HPalang.Core.Statements.DelayStatement;
import HPalang.LTSGeneration.LTSGenerator;
import HPalang.LTSGeneration.RunTimeStates.ActorRunTimeState;
import HPalang.LTSGeneration.RunTimeStates.GlobalRunTimeState;
import HPalang.LTSGeneration.TauLabel;
import HPalang.Core.Statements.SendStatement;

/**
 *
 * @author Iman Jahandideh
 */
public class MessageSendRule extends ActorLevelRule
{
    @Override
    protected boolean IsRuleSatisfied(ActorRunTimeState actorState, GlobalRunTimeState globalState)
    {
        return actorState.GetNextStatement() instanceof SendStatement && actorState.IsDelayed() == false;
    }

    @Override
    protected void ApplyToActorState(ActorRunTimeState actorState, GlobalRunTimeState globalState, LTSGenerator generator)
    {
        GlobalRunTimeState newGlobalState = globalState.DeepCopy();
        
        SendStatement sendStatement = (SendStatement)actorState.GetNextStatement();
        
          
        ActorRunTimeState senderState = newGlobalState.FindActorState(actorState.GetActor());
        ActorRunTimeState receiverState = newGlobalState.FindActorState(sendStatement.GetReceiver());
        
        senderState.DequeueNextStatement();
        
        if( GetMessageQueueSize(receiverState) < GetActorCapacity(receiverState))
            receiverState.EnqueueMessage(sendStatement.GetMessage());
        
        generator.AddTransition(new TauLabel(), newGlobalState);
    }
    
    private int GetMessageQueueSize(ActorRunTimeState state)
    {
        return state.GetMessages().size();
    }
    
    private int GetActorCapacity(ActorRunTimeState state)
    {
        return state.GetActor().GetCapacity();
    }
}
