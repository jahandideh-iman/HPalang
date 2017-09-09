/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.SpaceEx.Convertor;

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
    
    private Map<SoftwareActor, ActorModelData> actorsData = new HashMap<>();
    private Set<CommunicationLabel> globalSendLabels = new HashSet<>();
    
    private Map<CommunicationLabel, CommunicationLabel> receiveToSendMap = new HashMap<>();
    
    
    public HPalangModelData(ModelDefinition hpalangModel)
    {
        for(SoftwareActor actor : hpalangModel.SoftwareActors())
        {
            ActorModelData actorData = new ActorModelData(actor);
            actorsData.put(actor,actorData);
        }
        
//        for(SoftwareActor actor : hpalangModel.SoftwareActors())
//            for(MessageHandler handler : actor.GetMessageHandlers())
//                ProcessStatements(handler.GetBody(), actor, null);
                
        
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
