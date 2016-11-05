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
import HPalang.Statements.SendStatement;

/**
 *
 * @author Iman Jahandideh
 */
public class MessageSendRule extends ActorLevelRule
{
    @Override
    protected boolean IsRuleSatisfied(ActorRunTimeState actorState, GlobalRunTimeState globalState)
    {
        SendStatement sendStatement = null;
        if(actorState.GetNextStatement() instanceof SendStatement)
            sendStatement = (SendStatement) actorState.GetNextStatement();
        return  actorState.IsDelayed() == false 
                && sendStatement != null
                && globalState.FindActorState(sendStatement.GetReceiver()).GetDiscreteState().GetMessages().size() < sendStatement.GetReceiver().GetCapacity();
    }

    @Override
    protected void ApplyToActorState(ActorRunTimeState actorState, GlobalRunTimeState globalState, LTSGenerator generator)
    {
        GlobalRunTimeState newGlobalState = globalState.Clone();
        
        SendStatement sendStatement = (SendStatement)actorState.GetDiscreteState().GetNextStatement();
        
        ActorRunTimeState senderState = newGlobalState.FindActorState(actorState.GetActor());
        ActorRunTimeState receiverState = newGlobalState.FindActorState(sendStatement.GetReceiver());
        
        senderState.GetDiscreteState().DequeueNextStatement();
        
        receiverState.GetDiscreteState().EnqueueMessage(sendStatement.GetMessage());
        
        generator.AddTransition(new TauLabel(), newGlobalState);
    }
}
