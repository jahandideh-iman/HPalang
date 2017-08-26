/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Mocks;

import HPalang.Core.MessageArguments;
import HPalang.Core.Equalitable;
import HPalang.Core.Expression;
import HPalang.Core.Message;
import HPalang.Core.MessageParameters;
import HPalang.Core.Messages.MessageWithBody;
import HPalang.Core.Statement;
import HPalang.Core.VariableArgument;
import HPalang.Core.VariableParameter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import jdk.nashorn.internal.runtime.regexp.joni.constants.Arguments;

/**
 *
 * @author Iman Jahandideh
 */
public class FakeMessage extends Equalitable<FakeMessage> implements Message
{
    private final Queue<Statement> statements = new LinkedList<>();
    private final MessageParameters parameters =  new MessageParameters();
    
    private final MessageArguments arguments = new MessageArguments();
    
    public FakeMessage()
    {
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
    public void AddArgument(VariableArgument argument)
    {
        arguments.Add(argument);
    }
    
    @Override
    public MessageArguments Arguments()
    {
        return arguments;
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
    public void SetPriority(int priority)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int Priority()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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


}
