/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.SpaceEx.Convertor;

import HPalang.Core.ActorLocator;
import HPalang.Core.ActorLocators.ParametricActorLocator;
import HPalang.Core.ActorType;
import HPalang.Core.SoftwareActor;
import HPalang.Core.ContinuousVariable;
import HPalang.Core.InstanceParameter;
import HPalang.Core.Message;
import HPalang.Core.MessageHandler;
import HPalang.Core.MessageLocator;
import HPalang.Core.MessageLocators.DirectMessageLocator;
import HPalang.Core.Messages.NormalMessage;
import HPalang.Core.Statement;
import HPalang.Core.Statements.IfStatement;
import HPalang.Core.Statements.SendStatement;
import HPalang.Core.Variable;
import HPalang.Core.VariableArgument;
import HPalang.Core.VariableParameter;
import HPalang.Core.Variables.IntegerVariable;
import HPalang.LTSGeneration.RunTimeStates.ContinuousBehavior;
import HPalang.SpaceEx.Core.LabelParameter;
import static HPalang.SpaceEx.Convertor.Utilities.MiscUtilities.*;
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
    
    private final Set<SendStatement> sendStatements = new HashSet<>();

   
    private Set<CommunicationLabel> sendLabels = new HashSet<>();

    private Map<SendStatement, CommunicationLabel> sendLabelsMap = new HashMap<>();
    
    private final Map<InstanceParameter, ActorModelData> instanceBindings = new HashMap<>();

    private final HPalangModelData modelData;
    private SoftwareActor actor;
    
    private ActorQueueData queueData;
    
    private final Map<VariableParameter, String> messageParameterNames = new HashMap<>();
    
    private final Set<ActorModelData> receiversFromThisActor = new HashSet<>();
    private final Set<ActorModelData> sendersToThisActor = new HashSet<>();

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
        
        FindAllSendStatements(actor.Type());
        
        for(SendStatement sendStatement : sendStatements)
        {
            ActorModelData receiver = FindActorDataFor(sendStatement.ReceiverLocator());
            
            receiversFromThisActor.add(receiver);
            receiver.AddSender(this);
            
        }
        queueData.Init();
    }
    
    public void FindAllSendStatements(ActorType actorType)
    {
        for (MessageHandler handler : actor.Type().MessageHandlers()) 
            ExtractAllSendStatements(handler.GetBody());
    }
    
    public void ExtractAllSendStatements(Iterable<Statement> statements)
    {
        for(Statement statement :statements)
        {
            if(statement instanceof SendStatement)
                sendStatements.add((SendStatement) statement);
            else if(statement instanceof IfStatement)
            {
                IfStatement ifStatement = (IfStatement) statement;
                
                ExtractAllSendStatements(ifStatement.TrueStatements());
                ExtractAllSendStatements(ifStatement.FalseStatements());
            }
        }
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

    public Iterable<MessageHandler> MessageHandlers()
    {
        return actor.Type().MessageHandlers();
    }
    
    public Iterable<String> ExecuteMessageLabels()
    {
        List<String> labels = new LinkedList<>();
        
        for (MessageHandler m : MessageHandlers())
            labels.add(ExecuteLabelFor(m));
        
        return labels;
    }
    
    public String MessageHandlerName(MessageHandler handler)
    {
        return handler.GetID();
    }
    
//    public Collection<String> GetHandlerTakeLabels()
//    {
//        return handlerTakeLabels;
//    }
    
//    void AddSendLabel(SendStatement stat, String handler, SoftwareActor receiver,ContinuousBehavior ownerCB)
//    {
//        CommunicationLabel label = CreateSendLabel(handler, receiver, ownerCB);
//        sendLabels.add(label);
//        sendLabelsMap.put(stat, label);
//        if(ownerCB == null)
//            handlersSendLabels.add(label);
//    }
//    public Collection<CommunicationLabel> GetSendLables()
//    {
//        return sendLabels;
//    }
//    
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

    public String Name()
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

//    CommunicationLabel GetSelfSendLabelFor(CommunicationLabel selfReceive)
//    {
//        for(CommunicationLabel label : GetSendLables())
//            if(selfReceive.GetHandler().equals(label.GetHandler())
//            && label.IsSelf())
//                    return label;
//        return null;
//    }
    
//    public CommunicationLabel CreateSendLabel(String handler, SoftwareActor receiver, ContinuousBehavior ownerCB)
//    {
//        String actorName;
//        boolean isSelf = false;
//        
//        actorName = receiver.Name();
//        
//        if(receiver == actor)
//        {
//            isSelf = true;
//            actorName = "self";
//            if(ownerCB != null)
//                actorName+= "_" + GetIDFor(ownerCB);
//        }
//        
//        return new CommunicationLabel("Send_" + actorName + "_" + handler, handler, isSelf);
//    }
    
//    public CommunicationLabel CreateReceiveLabel(String handler, SoftwareActor sender,ContinuousBehavior ownerCB)
//    {
//        String actorName;
//        boolean isSelf = false;
//        
//        actorName = sender.Name();
//        
//        if(sender == actor)
//        {
//            isSelf = true;
//            actorName = "self";
//            if(ownerCB != null)
//                actorName+= "_" + GetIDFor(ownerCB);
//        }
//        
//        return new CommunicationLabel("Receive_" + actorName + "_" + handler, handler, isSelf);
//    }

//    Collection<CommunicationLabel> GetHandlersSendLables()
//    {
//        return handlersSendLabels;
//    }

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
    
    public String GuardFor(String variable, float value)
    {
        return GuardFor(variable, String.valueOf(value));
    }
    
    public String GuardFor(String variable, String value)
    {
        return String.format("%s == %s", variable, value);
    }
    
    public String FlowFor(String variable, float value)
    {
        return FlowFor(variable, String.valueOf(value));
    }
    
    public String FlowFor(String variable, String value)
    {
        return String.format("%s' == %s", variable, value);
    }
    
    public String InvarientFor(String variable, float value)
    {
        return InvarientFor(variable, String.valueOf(value));
    }
    
    public String InvarientFor(String variable, String value)
    {
        return String.format("%s == %s", variable, value);
    }

//    public String ReceiverMessageBufferEmptyGuard(SendStatement statement)
//    {
//        ActorModelData receiver = FindActorDataFor(statement.ReceiverLocator());
//        
//        return receiver.queueData.BufferIsEmptyGuard(ReceiverNameIn(statement.ReceiverLocator()));   
//    }

//    public String SetMessageBufferFullAssignment(SendStatement statement)
//    {
//        ActorModelData receiver = FindActorDataFor(statement.ReceiverLocator());
//
//        return receiver.queueData.SetBufferFullAssignment(ReceiverNameIn(statement.ReceiverLocator()));
//    }
    
    public String ReceiverBufferLabel(SendStatement statement)
    {
        ActorModelData receiver = FindActorDataFor(statement.ReceiverLocator());
        
        assert ( receiversFromThisActor.contains(receiver));
        

        
        return receiver.queueData.SendBufferLabelFor(this);
    }
    
    public String SetMessageBufferMessageAssignment(SendStatement statement)
    {
        ActorModelData receiver = FindActorDataFor(statement.ReceiverLocator());
        String receiverName = ReceiverNameIn(statement.ReceiverLocator());
        MessageHandler messageHandler = FindMessageHanlderFor(statement.MessageLocator());

        return receiver.queueData.MessageBufferAssignment(messageHandler, receiverName);
    }


    public ActorModelData FindActorDataFor(ActorLocator actorLocator)
    {
        if(actorLocator instanceof ParametricActorLocator)
        {
            return instanceBindings.get(((ParametricActorLocator) actorLocator).InstanceParameter());
        }
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public String ReceiverNameIn(ActorLocator actorLocator)
    {
        if (actorLocator instanceof ParametricActorLocator) {
            return ((ParametricActorLocator) actorLocator).InstanceParameter().Name();
        }
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public Message FindMessageFor(MessageLocator messageLocator)
    {
        if (messageLocator instanceof DirectMessageLocator) {
            return ((DirectMessageLocator)messageLocator).Locate(null);
        }
        throw new UnsupportedOperationException("Not supported yet."); 
    }
    
    private MessageHandler FindMessageHanlderFor(MessageLocator messageLocator)
    {
        Message message = FindMessageFor(messageLocator);
        if(message instanceof NormalMessage)
            return ((NormalMessage) message).GetMessageHandler();
        
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    public Iterable<String> BufferParameterAssignmentsFor(SendStatement statement)
    {
        List<String> assignments = new LinkedList<>();
        
        ActorModelData receiver = FindActorDataFor(statement.ReceiverLocator());
        String recieverName = ReceiverNameIn(statement.ReceiverLocator());
        Message message = FindMessageFor(statement.MessageLocator());
        
        List<VariableArgument> arguments = statement.Arguments().AsList();
        for(int i = 0 ; i< arguments.size(); i++)
        {
            assignments.add(
                    receiver.queueData.BufferParameterAssignmentFor(
                            message,
                            i,
                            arguments.get(i).Value().toString(),
                            recieverName));
        }

        return assignments;
    }

    public String ParameterNameFor(VariableParameter parameter)
    {
        return messageParameterNames.get(parameter);
    }

    public Iterable<String> AllSendVariables()
    {
        List<String> variabls = new LinkedList<>();
        
        for(SendStatement statement : sendStatements)
        {
            ActorModelData receiver = FindActorDataFor(statement.ReceiverLocator());
            String recieverName = ReceiverNameIn(statement.ReceiverLocator());
            Message message = FindMessageFor(statement.MessageLocator());
            
            variabls.add(receiver.queueData.BufferMessageVar(recieverName));
            //variabls.add(receiver.queueData.BufferIsEmptyVar(recieverName));
            
            List<VariableArgument> arguments = statement.Arguments().AsList();
            for (int i = 0; i < arguments.size(); i++) {
                VariableParameter parameter =  message.Parameters().AsList().get(i);
                variabls.add(receiver.queueData.BufferParamaterVarFor(parameter,recieverName));
            }
        }
        
        return variabls;
    }
    
    public Iterable<SendStatement> SendStatements()
    {
        return sendStatements;
    }
    
    public String BusyVar()
    {
        return "isBusy";
    }

    public String SetNotBusyAssignment()
    {
        return ResetFor(BusyVar(), 0);
    }
    
    public String SetBusyAssignment()
    {
        return ResetFor(BusyVar(), 1);
    }

    public String IsBusyGuard()
    {
        return GuardFor(BusyVar(), 1);
    }

    public String IsNotBusyGuard()
    {
        return GuardFor(BusyVar(), 0);
    }

    public String ReadyLabel()
    {
        return "Ready";
    }

    private void AddSender(ActorModelData sender)
    {
        sendersToThisActor.add(sender);
    }

    Iterable<ActorModelData> SendersToThisActor()
    {
        return sendersToThisActor;
    }

    Iterable<ActorModelData> RecieversFromThisActor()
    {
        return receiversFromThisActor;
    }
}
