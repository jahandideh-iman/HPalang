/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Mocks;

import HPalang.Core.Equalitable;
import HPalang.Core.Message;
import HPalang.Core.MessageParameters;
import HPalang.Core.Statement;
import HPalang.Core.VariableParameter;
import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author Iman Jahandideh
 */
public class FakeMessage extends Equalitable<FakeMessage> implements Message
{
    private final Queue<Statement> statements = new LinkedList<>();
    private final MessageParameters parameters =  new MessageParameters();
    
    public FakeMessage()
    {
    }
    
    public FakeMessage(Queue<Statement> statements)
    {
        this(statements, MessageParameters.From());
    }
        
    public FakeMessage(Queue<Statement> statements, MessageParameters parameters)
    {
        this.statements.addAll(statements);
        this.parameters.AddAll(parameters);
    }
    
    public void AddParameter(VariableParameter parameter)
    {
        parameters.Add(parameter);
    }
    
    @Override
    public Queue<Statement> GetMessageBody()
    {
        return statements;
    }
     
    @Override
    protected boolean InternalEquals(FakeMessage other)
    {
        return other.statements.equals(this.statements);
    }

    @Override
    protected int InternalHashCode()
    {
        return 0;
    }

    @Override
    public MessageParameters Parameters()
    {
        return parameters;
    }

    @Override
    public MessageType MessageType()
    {
        return MessageType.Control;
    }


}
