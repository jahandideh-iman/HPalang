/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.SpaceEx.Convertor;

import HPalang.Core.Actor;
import HPalang.Core.MessageHandler;
import HPalang.Core.Messages.NormalMessage;
import HPalang.Core.ProgramDefinition;
import HPalang.Core.Statement;
import HPalang.Core.Statements.ContinuousBehaviorStatement;
import HPalang.Core.Statements.SendStatement;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Iman Jahandideh
 */
public class HPalangModelData
{
    private Map<Actor, ActorModelData> actorsData = new HashMap<>();
    
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
                        String receiveLabel;
                        String sendLabel;
                        String sentHandler = sendStat.GetMessage().toString();
                        if(sendStat.GetReceiver() != actor)
                        {
                            receiveLabel = "Recieve_"+ actor.GetName() + "_" + sentHandler;
                            sendLabel = "Send_" + sendStat.GetReceiver().GetName() + "_" + sentHandler;
                        }
                        else
                        {
                            receiveLabel = "Recieve_"+ "self" + "_" + sentHandler;
                            sendLabel = "Send_" +"self"+ "_" + sentHandler;
                        }
                        
                        GetActorData(sendStat.GetReceiver()).AddReceiveLabel(receiveLabel, sentHandler);
                        GetActorData(actor).AddSendLabel(sendStat, sendLabel);
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
}
