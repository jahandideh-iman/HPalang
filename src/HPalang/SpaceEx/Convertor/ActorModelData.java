/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.SpaceEx.Convertor;

import HPalang.Core.Actor;
import HPalang.Core.ContinuousVariable;
import HPalang.Core.DiscreteVariable;
import HPalang.Core.MessageHandler;
import HPalang.Core.Statements.SendStatement;
import HPalang.LTSGeneration.RunTimeStates.ContinuousBehavior;
import java.sql.Struct;
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
public class ActorModelData
{


    private final Set<CommunicationLabel> receiveLabels = new HashSet<>();
    private final Map<String, List<CommunicationLabel>> handlersReceiveLabelMap = new HashMap<>();
    
    private final List<String> handlersName = new LinkedList<>();
    private final List<String> handlerTakeLabels = new LinkedList<>();
    private final List<ContinuousBehavior> continuousBehaviors = new LinkedList<>();
    private final Map<ContinuousBehavior,String> cBehaviorsID = new HashMap<>();

   
    private Set<CommunicationLabel> sendLabels = new HashSet<>();
    
    private Map<SendStatement, CommunicationLabel> sendLabelsMap = new HashMap<>();

    private Actor actor;

    public ActorModelData(Actor actor)
    {
        this.actor = actor;
        for (MessageHandler handler : actor.GetMessageHandlers()) 
        {
            handlersName.add(handler.GetID());
            handlerTakeLabels.add(CreateTakeLabel(handler.GetID()));
            handlersReceiveLabelMap.put(handler.GetID(), new LinkedList<>());
               
        }
    }
    
    public Actor GetActor()
    {
        return actor;
    }

    public final String CreateTakeLabel(String handler)
    {
        return "Take_" + handler;
    }
    
    public String GetUrgentVar()
    {
        return "urg";
    }

    public void AddReceiveHandler(String handler, Actor sender)
    {    
        CommunicationLabel label = CreateReceiveLabel(handler, sender);
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

    public Collection<String> GetHandlersName()
    {
        return handlersName;
    }
    
    public Collection<String> GetHandlerTakeLabels()
    {
        return handlerTakeLabels;
    }
    
    void AddSendLabel(SendStatement stat, String handler, Actor receiver)
    {
        CommunicationLabel label = CreateSendLabel(handler, receiver);
        sendLabels.add(label);
        sendLabelsMap.put(stat, label);
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
        return actor.GetContinuousVariables();
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

    String GetUrgentReset()
    {
        return GetUrgentVar() + ":= 0";
    }

    String GetStartLabelFor(ContinuousBehavior behavior)
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
    String GetIsBusyGuard()
    {
        return GetBusyVar() + " == 1";
    }
    
    String GetIsNotBusyGuard()
    {
        return GetBusyVar() + " == 0";
    }

    String GetBusyInvarient()
    {
        return GetBusyVar() + " == 1";
    }

    String GetName()
    {
        return actor.GetName();
    }
    
    public String GetDelayVar()
    {
        return actor.GetDelayVariable().Name();
    }

    Collection<DiscreteVariable> GetDiscreteVaraible()
    {
        return actor.GetDiscreteVariables().keySet();
    }

    CommunicationLabel GetSelfSendLabelFor(CommunicationLabel selfReceive)
    {
        for(CommunicationLabel label : GetSendLables())
            if(selfReceive.GetHandler().equals(label.GetHandler())
            && label.IsSelf())
                    return label;
        return null;
    }
    
    public CommunicationLabel CreateSendLabel(String handler, Actor receiver)
    {
        String actorName;
        boolean isSelf = false;
        
        actorName = receiver.GetName();
        
        if(receiver == actor)
        {
            isSelf = true;
            actorName = "self";
        }
        
        return new CommunicationLabel("Send_" + actorName + "_" + handler, handler, isSelf);
    }
    
    public CommunicationLabel CreateReceiveLabel(String handler, Actor sender)
    {
        String actorName;
        boolean isSelf = false;
        
        actorName = sender.GetName();
        
        if(sender == actor)
        {
            isSelf = true;
            actorName = "self";
        }
        
        return new CommunicationLabel("Receive_" + actorName + "_" + handler, handler, isSelf);
    }
}
