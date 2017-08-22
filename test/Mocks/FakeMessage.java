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
import HPalang.Core.VariableParameter;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 *
 * @author Iman Jahandideh
 */
public class FakeMessage extends Equalitable<FakeMessage> implements Message
{
    private Queue<Statement> statements = new LinkedList<>();
    private MessageParameters parameters =  new MessageParameters();
    
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
