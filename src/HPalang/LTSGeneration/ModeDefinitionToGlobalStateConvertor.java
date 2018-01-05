/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration;

import HPalang.Core.Actor;
import HPalang.Core.ActorLocators.DirectActorLocator;
import HPalang.Core.Message;
import HPalang.Core.MessagePacket;
import HPalang.LTSGeneration.RunTimeStates.GlobalRunTimeState;
import HPalang.Core.ModelDefinition;
import HPalang.Core.SimpleRealVariablePool;
import HPalang.Core.Statements.SendStatement;
import HPalang.LTSGeneration.RunTimeStates.ActorState;
import static HPalang.LTSGeneration.Utilities.CreationUtility.*;
import static HPalang.LTSGeneration.Utilities.FillerUtility.*;

/**
 *
 * @author Iman Jahandideh
 */
public class ModeDefinitionToGlobalStateConvertor
{
    public GlobalRunTimeState Convert(ModelDefinition model)
    {
        
        GlobalRunTimeState globalState = CreateEmptyGlobalState();
        
        FillDiscreteState(globalState, model.SoftwareActors());
        FillContinuousState(globalState, model.PhysicalActors());
        
        globalState.EventsState().PoolState().SetPool(new SimpleRealVariablePool("EventStatePool",model.EventSystemVariablePoolSize()));
        globalState.VariablePoolState().SetPool(new SimpleRealVariablePool("GlobalPool",model.GlobalVariablePoolSize()));
        
        
        for(SendStatement sendStatement : model.GetInitialSends())
        {
            assert (sendStatement.Arguments().AsList().isEmpty());
            
            DirectActorLocator directActorLocator = (DirectActorLocator) sendStatement.ReceiverLocator();
            
            Actor actor = directActorLocator.Locate(null);
            Message message = sendStatement.MessageLocator().Locate(null);
            ActorState actorState =  globalState.FindActorState(actor);
            
            actorState.MessageQueueState().Messages().Enqueue(
                    new MessagePacket(actor, actor, message, sendStatement.Arguments()));
            
        }
        
        return globalState;
    }
}
