/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.SpaceEx.Convertor;

import HPalang.Core.Actor;
import HPalang.Core.ActorType;
import HPalang.Core.SoftwareActor;
import HPalang.Core.MessageHandler;
import HPalang.Core.ModelDefinition;
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
        
        for(ActorModelData actorData : actorsData.values())
            actorData.Init();
               
        
    }
    
    public int MessageGUID(MessageHandler messageHandler)
    {
        return messageHandlersGUID.get(messageHandler);
    }
        
    ActorTypeModelData GetActorTypeData(ActorType type)
    {
        return actorTypesData.get(type);
    }
    
    public final ActorModelData ActorModelDataFor(Actor actor)
    {
        if(actor instanceof SoftwareActor)
            return actorsData.get(actor);
        else
            throw new RuntimeException("Physical actor model data is not yet implemented");
    }
    
    Collection<CommunicationLabel> GetGlobalSendLabels()
    {
        return globalSendLabels;
    }

    CommunicationLabel GetSendLabelFor(CommunicationLabel receive)
    {
        return receiveToSendMap.get(receive);
            
    }

    Iterable<ActorModelData> ActorModelsData()
    {
        return actorsData.values();
    }
}
