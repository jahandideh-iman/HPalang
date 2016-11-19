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

/**
 *
 * @author Iman Jahandideh
 */
public class EmptyMessage extends Equalitable<EmptyMessage> implements Message
{
    private final String id;
    
    public EmptyMessage()
    {
        id = "";
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
    
}
