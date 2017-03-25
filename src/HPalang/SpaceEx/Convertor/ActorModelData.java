/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.SpaceEx.Convertor;

import HPalang.Core.Actor;
import HPalang.Core.ContinuousVariable;
import HPalang.Core.MessageHandler;
import HPalang.LTSGeneration.RunTimeStates.ContinuousBehavior;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Iman Jahandideh
 */
public class ActorModelData
{

    private List<String> receiveLabels = new LinkedList<>();
    private List<String> handlersName = new LinkedList<>();
    private List<ContinuousBehavior> continuousBehaviors = new LinkedList<>();

    private Map<String, List<String>> handlersReceiveLabelMap = new HashMap<>();

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

    public Collection<ContinuousBehavior> GetContinuousBehaviors()
    {
        return continuousBehaviors;
    }

    public void AddContinuousBehavior(ContinuousBehavior behavior)
    {
        continuousBehaviors.add(behavior);
    }

    public Collection<ContinuousVariable> GetContinuousVariables()
    {
        return actor.GetContinuousVariables().keySet();
    }
}
