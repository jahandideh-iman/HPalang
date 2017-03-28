/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.SpaceEx.Convertor;

import HPalang.Core.Actor;
import HPalang.Core.MessageHandler;
import HPalang.Core.ProgramDefinition;
import HPalang.Core.Statement;
import HPalang.Core.Statements.ContinuousBehaviorStatement;
import HPalang.Core.Statements.SendStatement;
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
    private Map<Actor, ActorModelData> actorsData = new HashMap<>();
    private Set<CommunicationLabel> globalSendLabels = new HashSet<>();
    
    private Map<CommunicationLabel, CommunicationLabel> receiveToSendMap = new HashMap<>();
    
    
    public HPalangModelData(ProgramDefinition hpalangModel)
    {
        for(Actor actor : hpalangModel.GetActors())
        {
            ActorModelData actorData = new ActorModelData(actor);
            actorsData.put(actor,actorData);
        }
        
        for(Actor actor : hpalangModel.GetActors())
        {
            for(MessageHandler handler : actor.GetMessageHandlers())
                for(Statement stat : handler.GetBody())
                {
                    if(stat instanceof SendStatement)
                    {
                        SendStatement sendStat = (SendStatement) stat;
                        String handlerId = sendStat.GetMessage().toString();                     
                        GetActorData(sendStat.GetReceiver()).AddReceiveHandler(handlerId, actor);
                        GetActorData(actor).AddSendLabel(sendStat, handlerId, sendStat.GetReceiver());
                        
                        CommunicationLabel sendLabel =  GetActorData(actor).CreateSendLabel(handlerId, sendStat.GetReceiver());
                        CommunicationLabel receiveLabel = GetActorData(sendStat.GetReceiver()).CreateReceiveLabel(handlerId, actor);
                        if(sendLabel.IsSelf() == false)
                        {
                            globalSendLabels.add(sendLabel);
                            receiveToSendMap.put(receiveLabel, sendLabel);
                        }
                        
                    }
                    if(stat instanceof ContinuousBehaviorStatement)
                    {
                        ContinuousBehaviorStatement cbStat = (ContinuousBehaviorStatement)stat;
                        GetActorData(actor).AddContinuousBehavior(cbStat.GetBehavior());
                        
                    }
                }
        }
    }
    
    public final ActorModelData GetActorData(Actor actor)
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
