/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.SOSRules;

import HPalang.LTSGeneration.LTSGenerator;
import HPalang.LTSGeneration.RunTimeStates.ActorRunTimeState;
import HPalang.LTSGeneration.GlobalRunTimeState;
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
        if((actorState.StatementQueue().Head() instanceof SendStatement) == false)
            return false;
        
        SendStatement sendStatement = (SendStatement)actorState.StatementQueue().Head();
        ActorRunTimeState receiverState = globalState.FindActorState(sendStatement.GetReceiver());
        
        return  receiverState.LowPriorityMessageQueue().Size() >= receiverState.GetMessageQueueCapacity();
    }

    @Override
    protected void ApplyToActorState(ActorRunTimeState actorState, GlobalRunTimeState globalState, LTSGenerator generator)
    {
        GlobalRunTimeState newGlobalState = globalState.DeepCopy();
               
        ActorRunTimeState senderState = newGlobalState.FindActorState(actorState.GetActor());
        
        senderState.StatementQueue().Dequeue();
        
        generator.AddTransition(new TauLabel(), newGlobalState);
    }
}
