/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Mocks;

import HPalang.Core.Message;
import HPalang.Core.Statement;
import java.util.LinkedList;
import java.util.Queue;
import HPalang.Core.Equalitable;
import HPalang.Core.MessageArguments;
import HPalang.Core.MessageParameters;
import HPalang.Core.VariableArgument;

/**
 *
 * @author Iman Jahandideh
 */
public class EmptyMessage extends Equalitable<EmptyMessage> implements Message
{
    private final String id;
    private int priority;
    
    public EmptyMessage()
    {
        id = "";
    }
    
    public EmptyMessage(String id, int priority)
    {
        this.id = id;
        this.priority = priority;
    }
    
    public EmptyMessage(String id)
    {
        this.id = id;
    }
    
    @Override
    public Queue<Statement> GetMessageBody()
    {
        return new LinkedList<>();
    }
    
    
    @Override
    protected boolean InternalEquals(EmptyMessage other)
    {
        return this.id.equals(other.id);
    }

    @Override
    protected int InternalHashCode()
    {
       return 1;
    }

    @Override
    public void SetPriority(int priority)
    {
        this.priority = priority;
    }

    @Override
    public int Priority()
    {
        return priority;
    }

    @Override
    public MessageParameters Parameters()
    {
        return new MessageParameters();
    } 
}
