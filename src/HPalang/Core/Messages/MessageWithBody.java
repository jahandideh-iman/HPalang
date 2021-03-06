/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Core.Messages;

import HPalang.Core.Equalitable;
import HPalang.Core.Statement;
import HPalang.Core.Message;
import HPalang.Core.MessageParameters;
import java.util.Queue;

/**
 *
 * @author Iman Jahandideh
 */
public class MessageWithBody extends Equalitable<MessageWithBody> implements Message
{
    private final Queue<Statement> statements;
    private final MessageType messageType;
    
    public MessageWithBody(Queue<Statement> statements, MessageType messageType)
    {
        this.statements = statements;
        this.messageType = messageType;
    }

    @Override
    public Queue<Statement> GetMessageBody()
    {
        return statements;
    }
     
    @Override
    protected boolean InternalEquals(MessageWithBody other)
    {
        return other.statements.equals(this.statements);
    }

    @Override
    protected int InternalHashCode()
    {
        return statements.hashCode();
    }
    
    @Override
    public String toString()
    {
        String out = "{";
        
        for(Statement s : statements)
            out += s.toString() + ",";
        
        return out + "}";
    }

    @Override
    public MessageParameters Parameters()
    {
        return new MessageParameters();
    }

    @Override
    public MessageType MessageType()
    {
        return messageType;
    }
}
