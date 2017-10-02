/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.SpaceEx.Convertor;

import HPalang.Core.ActorType;
import HPalang.Core.SoftwareActor;
import HPalang.Core.MessageHandler;
import HPalang.Core.ModelDefinition;
import HPalang.Core.Statement;
import HPalang.Core.Statements.SendStatement;
import HPalang.LTSGeneration.RunTimeStates.ContinuousBehavior;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Iman Jahandideh
 */
public class HPalangModelData
{
    
    private final Map<SoftwareActor, ActorModelData> actorsData = new HashMap<>();
    private final Set<CommunicationLabel> globalSendLabels = new HashSet<>();
    
    private final Map<CommunicationLabel, CommunicationLabel> receiveToSendMap = new HashMap<>();
    
    private final Map<ActorType, ActorTypeModelData> actorTypesData = new HashMap<>();
    
    private final Map<MessageHandler, Integer> messageHandlersGUID = new HashMap<>();
    
    
    public HPalangModelData(ModelDefinition hpalangModel)
    {
        int messageGUID = 0;
        for(ActorType type : hpalangModel.ActorTypes())
        {
            for(MessageHandler handler : type.MessageHandlers())
            {
                messageHandlersGUID.put(handler, messageGUID);
                messageGUID++;
            }
            actorTypesData.put(type, new ActorTypeModelData(type));
        }
        
        for(SoftwareActor actor : hpalangModel.SoftwareActors())
            actorsData.put(actor,new ActorModelData(actor, this));
        
        
//        for(SoftwareActor actor : hpalangModel.SoftwareActors())
//            for(MessageHandler handler : actor.GetMessageHandlers())
//                ProcessStatements(handler.GetBody(), actor, null);
                
        
    }
    
    public int MessageGUID(MessageHandler messageHandler)
    {
        return messageHandlersGUID.get(messageHandler);
    }
        
    private void ProcessStatements(Collection<Statement> statements, SoftwareActor actor, ContinuousBehavior ownerCB )
    {
        for (Statement stat :statements) {
            if (stat instanceof SendStatement) {
                SendStatement sendStat = (SendStatement) stat;
                String handlerId = sendStat.MessageLocator().toString();
                //GetActorData(sendStat.ReceiverLocator()).AddReceiveHandler(handlerId, actor, ownerCB);
                //GetActorData(actor).AddSendLabel(sendStat, handlerId, sendStat.ReceiverLocator(), ownerCB);

//                CommunicationLabel sendLabel = GetActorData(actor).CreateSendLabel(handlerId, sendStat.ReceiverLocator(), ownerCB);
//                CommunicationLabel receiveLabel = GetActorData(sendStat.ReceiverLocator()).CreateReceiveLabel(handlerId, actor, ownerCB);
//                if (sendLabel.IsSelf() == false) {
//                    globalSendLabels.add(sendLabel);
//                    receiveToSendMap.put(receiveLabel, sendLabel);
//                }

            }
//            else if (stat instanceof ContinuousBehaviorStatement) {
//                ContinuousBehaviorStatement cbStat = (ContinuousBehaviorStatement) stat;
//                GetActorData(actor).AddContinuousBehavior(cbStat.GetBehavior());
//                ProcessStatements(cbStat.GetBehavior().GetActions(), actor, cbStat.GetBehavior());
//            }
        }
    }
    
    ActorTypeModelData GetActorTypeData(ActorType type)
    {
        return actorTypesData.get(type);
    }
    
    public final ActorModelData GetActorData(SoftwareActor actor)
    {
        return actorsData.get(actor);
    }
    
    Collection<CommunicationLabel> GetGlobalSendLabels()
    {
        return globalSendLabels;
    }

    CommunicationLabel GetSendLabelFor(CommunicationLabel receive)
    {
        return receiveToSendMap.get(receive);
            
    }


}
