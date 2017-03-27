/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.SpaceEx.Convertor;

import HPalang.Core.Actor;
import HPalang.Core.ContinuousVariable;
import HPalang.Core.MessageHandler;
import HPalang.Core.Statement;
import HPalang.Core.Statements.SendStatement;
import HPalang.LTSGeneration.RunTimeStates.ContinuousBehavior;
import HPalang.SpaceEx.Core.Invarient;
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

    private Set<String> receiveLabels = new HashSet<>();
    private List<String> handlersName = new LinkedList<>();
    private List<ContinuousBehavior> continuousBehaviors = new LinkedList<>();
    private Map<ContinuousBehavior,String> cBehaviorsID = new HashMap<>();

    private Map<String, List<String>> handlersReceiveLabelMap = new HashMap<>();
    
    private Set<String> sendLabels = new HashSet<>();
    private Map<SendStatement, String> sendLabelsMap = new HashMap<>();

    private Actor actor;

    public ActorModelData(Actor actor)
    {
        this.actor = actor;
        for (MessageHandler handler : actor.GetMessageHandlers()) 
        {
            handlersName.add(handler.GetID());
            handlersReceiveLabelMap.put(handler.GetID(), new LinkedList<>());
               
        }
    }
    
    public Actor GetActor()
    {
        return actor;
    }

    public String CreateTakeLabel(String handler)
    {
        return "Take_" + handler;
    }
    
    public String GetUrgentVar()
    {
        return "urg";
    }

    public void AddReceiveLabel(String label, String handler)
    {
        receiveLabels.add(label);
        handlersReceiveLabelMap.get(handler).add(label);
    }

    public Collection<String> GetReceiveLabels()
    {
        return receiveLabels;
    }

    public Collection<String> GetReceiveLabelsFor(String handler)
    {

        return handlersReceiveLabelMap.get(handler);
    }

    public Collection<String> GetHandlersName()
    {
        return handlersName;
    }
    
    void AddSendLabel(SendStatement stat, String sendLabel)
    {
        sendLabels.add(sendLabel);
        sendLabelsMap.put(stat, sendLabel);
    }
    public Collection<String> GetSendLables()
    {
        return sendLabels;
    }
    
    String GetSendLabelFor(SendStatement statement)
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
}
