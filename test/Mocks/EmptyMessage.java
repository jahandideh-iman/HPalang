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
    private final MessageType type;
    
    public EmptyMessage()
    {
        this("empty");
    }
    
    public EmptyMessage(int priority)
    {
        this("empty", 0);
    }
    
    public EmptyMessage(String id, MessageType type)
    {
        this(id, 0, type);
        
    }
    
    public EmptyMessage(String id, int priority)
    {
        this(id,priority, MessageType.Control);
    }
    
    public EmptyMessage(String id, int priority, MessageType type)
    {
        this.id = id;
        this.priority = priority;
        this.type = type;
    }
    
    public EmptyMessage(String id)
    {
        this(id, 0);
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
    public int Priority()
    {
        return priority;
    }

    @Override
    public MessageParameters Parameters()
    {
        return new MessageParameters();
    } 

    @Override
    public MessageType MessageType()
    {
        return type;
    }
}
