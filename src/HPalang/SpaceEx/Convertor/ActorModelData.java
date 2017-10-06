/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.SpaceEx.Convertor;

import HPalang.Core.ActorLocator;
import HPalang.Core.ActorLocators.ParametricActorLocator;
import HPalang.Core.SoftwareActor;
import HPalang.Core.ContinuousVariable;
import HPalang.Core.InstanceParameter;
import HPalang.Core.Message;
import HPalang.Core.MessageHandler;
import HPalang.Core.MessageLocator;
import HPalang.Core.MessageLocators.DirectMessageLocator;
import HPalang.Core.Statements.SendStatement;
import HPalang.Core.Variable;
import HPalang.Core.VariableArgument;
import HPalang.Core.VariableParameter;
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
    
    private final Map<InstanceParameter, ActorModelData> instanceBindings = new HashMap<>();

    private final HPalangModelData modelData;
    private SoftwareActor actor;
    
    private ActorQueueData queueData;
    
    private final Map<VariableParameter, String> messageParameterNames = new HashMap<>();

    public ActorModelData(SoftwareActor actor, HPalangModelData modelData)
    {
        this.modelData = modelData;
        this.actor = actor;
        
        this.queueData = new ActorQueueData(this);
    }
    
    public void Init()
    {
        for (InstanceParameter param : actor.Type().InstanceParameters()) {
            instanceBindings.put(
                    param,
                    modelData.ActorModelDataFor(actor.GetInstanceFor(param)));
        }
        
        for (MessageHandler handler : actor.Type().MessageHandlers()) 
        {
            for( VariableParameter parameter : handler.Parameters().AsList())
                messageParameterNames.put(
                        parameter,
                        String.format("%s_%s", handler.GetID(), parameter.Name()));
        }
        
        queueData.Init();
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
        return modelData.MessageGUID(messageHandler);
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
    
    public CommunicationLabel GetSendLabelFor(SendStatement statement)
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

    public String GetName()
    {
        return actor.Name();
    }
    
    public String DelayVar()
    {
        return "delay";
    }

    Collection<IntegerVariable> GetDiscreteVaraible()
    {
        //return actor.Type().InstanceVariables();
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

    public Iterable<Variable> InstanceVariables()
    {
        return actor.Type().Variables();
    }
    
    public Iterable<String> MessageParameterNames()
    {
        return messageParameterNames.values();
    }
    
    public Iterable<VariableParameter> MessageParameters()
    {
        return messageParameterNames.keySet();
    }

    public String DelayInvarient(float delay)
    {
        return String.format("%s <= %s", DelayVar(), delay);
    }

    public String DelayFlow()
    {
        return String.format("%s' == 1", DelayVar());
    }

    public String ResetFor(String variable, float value)
    {
        return ResetFor(variable, String.valueOf(value));
    }
    
    public String ResetFor(String variable, String value)
    {
        return String.format("%s := %s", variable, value);
    }

    public String ReceiverMessageBufferEmptyGuard(SendStatement statement)
    {
        ActorModelData receiver = FindActorDataFor(statement.ReceiverLocator());
        
        return receiver.queueData.BufferIsEmptyGuard(receiver.actor.Name());   
    }

    public String SetMessageBufferFullAssignment(SendStatement statement)
    {
        ActorModelData receiver = FindActorDataFor(statement.ReceiverLocator());

        return receiver.queueData.SetBufferFullAssignment(receiver.actor.Name());
    }

    public ActorModelData FindActorDataFor(ActorLocator actorLocator)
    {
        if(actorLocator instanceof ParametricActorLocator)
        {
            return instanceBindings.get(((ParametricActorLocator) actorLocator).InstanceParameter());
        }
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    private String ReceiverNameIn(ActorLocator actorLocator)
    {
        if (actorLocator instanceof ParametricActorLocator) {
            return ((ParametricActorLocator) actorLocator).InstanceParameter().Name();
        }
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    private Message FindMessageHandlerFor(MessageLocator messageLocator)
    {
        if (messageLocator instanceof DirectMessageLocator) {
            return ((DirectMessageLocator)messageLocator).Locate(null);
        }
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    public Iterable<String> BufferParameterAssignmentsFor(SendStatement statement)
    {
        List<String> assignments = new LinkedList<>();
        
        ActorModelData receiver = FindActorDataFor(statement.ReceiverLocator());
        String recieverName = ReceiverNameIn(statement.ReceiverLocator());
        Message message = FindMessageHandlerFor(statement.MessageLocator());
        
        List<VariableArgument> arguments = statement.Arguments().AsList();
        for(int i = 0 ; i< arguments.size(); i++)
        {
            assignments.add(
                    receiver.queueData.BufferParameterAssignmentFor(
                            message,
                            i,
                            arguments.get(i).Value().toString(),
                            receiver.actor.Name()));
        }

        return assignments;
    }

    public String FlowFor(String variableName, float flow)
    {
        return String.format("%s' == %s", variableName, flow);
    } 

    public String ParameterNameFor(VariableParameter parameter)
    {
        return messageParameterNames.get(parameter);
    }


}
