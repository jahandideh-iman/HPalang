/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Core;

import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author Iman Jahandideh
 */
public class MessageHandler
{
    private final Queue<Statement> statements = new LinkedList<>();
    private SoftwareActor owner;
    private String id;
    private final MessageParameters parameters = new MessageParameters();
    private final Message.MessageType messageType;

    public MessageHandler(Message.MessageType messageType)
    {
        this.messageType = messageType;
    }  
    public void SetOwner(SoftwareActor owner)
    {
        this.owner = owner;
    }
    
    public void AddStatement(Statement statement)
    {
        statements.add(statement);
    }
    
    public Queue<Statement> GetBody()
    {
        return statements;
    }
    
    public void SetID(String id)
    {
        this.id = id;
    }

    public String GetID()
    {
       return id;
    }

    @Deprecated // Use Parameters().Add
    public void AddParameter(VariableParameter variableParameter)
    {
        parameters.Add(variableParameter);
    }

    @Deprecated // Use Parameters().Find
    public VariableParameter FindVariableParameter(String parameterName)
    {
        return parameters.Find(parameterName);
    }
    
    public  MessageParameters Parameters()
    {
        return parameters;
    }

    public Message.MessageType MessageType()
    {
        return messageType;
    }
}
