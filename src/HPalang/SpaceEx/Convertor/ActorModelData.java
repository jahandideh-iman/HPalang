/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.SpaceEx.Convertor;

import HPalang.Core.SoftwareActor;
import HPalang.Core.ContinuousVariable;
import HPalang.Core.MessageHandler;
import HPalang.Core.Statements.SendStatement;
import HPalang.Core.Variables.IntegerVariable;
import HPalang.LTSGeneration.RunTimeStates.ContinuousBehavior;
import java.util.Collection;
import java.util.Collections;
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
public class ActorModelData
{
    private final Set<CommunicationLabel> receiveLabels = new HashSet<>();
    private final Map<String, List<CommunicationLabel>> handlersReceiveLabelMap = new HashMap<>();
    
    //private final List<String> handlersName = new LinkedList<>();

    
    private final List<String> handlerTakeLabels = new LinkedList<>();
    private final List<ContinuousBehavior> continuousBehaviors = new LinkedList<>();
    private final Map<ContinuousBehavior,String> cBehaviorsID = new HashMap<>();

   
    private Set<CommunicationLabel> sendLabels = new HashSet<>();
    private Set<CommunicationLabel> handlersSendLabels = new HashSet<>();
    
    private Map<SendStatement, CommunicationLabel> sendLabelsMap = new HashMap<>();

    private final HPalangModelData modelDatal;
    private SoftwareActor actor;
    
    private ActorQueueData queueData;

    public ActorModelData(SoftwareActor actor, HPalangModelData modelData)
    {
        this.modelDatal = modelData;
        this.actor = actor;
//        for (MessageHandler handler : actor.Type().MessageHandlers()) 
//        {
//            handlersName.add(handler.GetID());
//            handlerTakeLabels.add(TakeLabelFor(handler.GetID()));
//            handlersReceiveLabelMap.put(handler.GetID(), new LinkedList<>());
//               
//        }
                
        this.queueData = new ActorQueueData(this);

    }

    
    public SoftwareActor Actor()
    {
        return actor;
    }
    
    public ActorQueueData QueueData()
    {
        return queueData;
    }

    public int MessageGUID(MessageHandler messageHandler)
    {
        return modelDatal.MessageGUID(messageHandler);
    }
    
    public final String TakeLabelFor(MessageHandler handler)
    {
        return "Take_" + MessageHandlerName(handler);
    }
    
    public final String ExecuteLabelFor(MessageHandler handler)
    {
        return "Execute_" + MessageHandlerName(handler);
    }
    
    public String GetUrgentVar()
    {
        return "urg";
    }

    public void AddReceiveHandler(String handler, SoftwareActor sender, ContinuousBehavior ownerCB)
    {    
        CommunicationLabel label = CreateReceiveLabel(handler, sender,ownerCB);
        receiveLabels.add(label);
        handlersReceiveLabelMap.get(handler).add(label);
    }

    public Collection<CommunicationLabel> GetReceiveLabels()
    {
        return receiveLabels;
    }

    public Collection<CommunicationLabel> GetReceiveLabelsFor(String handler)
    {
        return handlersReceiveLabelMap.get(handler);
    }

    public Iterable<MessageHandler> MessageHandlers()
    {
        return actor.Type().MessageHandlers();
    }
    
    public String MessageHandlerName(MessageHandler handler)
    {
        return handler.GetID();
    }
    
    public Collection<String> GetHandlerTakeLabels()
    {
        return handlerTakeLabels;
    }
    
    void AddSendLabel(SendStatement stat, String handler, SoftwareActor receiver,ContinuousBehavior ownerCB)
    {
        CommunicationLabel label = CreateSendLabel(handler, receiver, ownerCB);
        sendLabels.add(label);
        sendLabelsMap.put(stat, label);
        if(ownerCB == null)
            handlersSendLabels.add(label);
    }
    public Collection<CommunicationLabel> GetSendLables()
    {
        return sendLabels;
    }
    
    CommunicationLabel GetSendLabelFor(SendStatement statement)
    {
        return sendLabelsMap.get(statement);
    }

    public Collection<ContinuousBehavior> GetContinuousBehaviors()
    {
        return continuousBehaviors;
    }

    public void AddContinuousBehavior(ContinuousBehavior behavior)
    {
        continuousBehaviors.add(behavior);
        cBehaviorsID.put(behavior, String.valueOf(continuousBehaviors.size()));
    }

    public Collection<ContinuousVariable> GetContinuousVariables()
    {
        //return actor.GetContinuousVariables();
        return Collections.EMPTY_LIST;
    }

    public String GetUrgentInvarient()
    {
        return GetUrgentVar() + " == 0";
    }

    public String GetUrgentGuard()
    {
        return GetUrgentVar() + "== 0";
    }
    
    public String GetUrgentFlow()
    {
        return GetUrgentVar()+"'" + " == 1";
    }

    public String GetUrgentReset()
    {
        return GetUrgentVar() + ":= 0";
    }

    public String GetStartLabelFor(ContinuousBehavior behavior)
    {
        return "Start_CB_" + cBehaviorsID.get(behavior);
    }
    
    String GetIDFor(ContinuousBehavior behavior)
    {
        return cBehaviorsID.get(behavior);
    }

    String GetLockVar()
    {
        return "lock";
    }

    String GetLockReleaseReset()
    {
        return GetLockVar() + ":= 0";
    }
    
    String GetLockGainReset()
    {
        return GetLockVar() + ":= 1";
    }

    String GetLockGainGuard()
    {
        return GetLockVar() + "== 0";
    }

    public String GetBusyVar()
    {
        return "actorBusy";
    }
    
    public String GetBusyAssignment()
    {
        return GetBusyVar() + " := 1";
    }
    
    public String GetUnBusyAssignment()
    {
        return GetBusyVar() + " := 0";
    }
    
    public String GetIsBusyGuard()
    {
        return GetBusyVar() + " == 1";
    }
    
    public String GetIsNotBusyGuard()
    {
        return GetBusyVar() + " == 0";
    }

    public String GetBusyInvarient()
    {
        return GetBusyVar() + " == 1";
    }

    public String GetName()
    {
        return actor.Name();
    }
    
    public String GetDelayVar()
    {
        //return actor.GetDelayVariable().Name();
        return "";
    }

    Collection<IntegerVariable> GetDiscreteVaraible()
    {
        //return actor.Type().Variables();
        return Collections.EMPTY_LIST;
    }

    CommunicationLabel GetSelfSendLabelFor(CommunicationLabel selfReceive)
    {
        for(CommunicationLabel label : GetSendLables())
            if(selfReceive.GetHandler().equals(label.GetHandler())
            && label.IsSelf())
                    return label;
        return null;
    }
    
    public CommunicationLabel CreateSendLabel(String handler, SoftwareActor receiver, ContinuousBehavior ownerCB)
    {
        String actorName;
        boolean isSelf = false;
        
        actorName = receiver.Name();
        
        if(receiver == actor)
        {
            isSelf = true;
            actorName = "self";
            if(ownerCB != null)
                actorName+= "_" + GetIDFor(ownerCB);
        }
        
        return new CommunicationLabel("Send_" + actorName + "_" + handler, handler, isSelf);
    }
    
    public CommunicationLabel CreateReceiveLabel(String handler, SoftwareActor sender,ContinuousBehavior ownerCB)
    {
        String actorName;
        boolean isSelf = false;
        
        actorName = sender.Name();
        
        if(sender == actor)
        {
            isSelf = true;
            actorName = "self";
            if(ownerCB != null)
                actorName+= "_" + GetIDFor(ownerCB);
        }
        
        return new CommunicationLabel("Receive_" + actorName + "_" + handler, handler, isSelf);
    }

    Collection<CommunicationLabel> GetHandlersSendLables()
    {
        return handlersSendLabels;
    }

}
