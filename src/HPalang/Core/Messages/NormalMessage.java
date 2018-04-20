/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Core.Messages;

import HPalang.Core.Equalitable;
import HPalang.Core.Message;
import HPalang.Core.MessageHandler;
import HPalang.Core.MessageParameters;
import HPalang.Core.Statement;
import java.util.Queue;

/**
 *
 * @author Iman Jahandideh
 */
public class NormalMessage extends Equalitable<NormalMessage> implements Message
{
    private final MessageHandler messageHandler;

    public NormalMessage(MessageHandler messageHandler)
    {
        this.messageHandler = messageHandler;
    }
    
    @Override
    public MessageType MessageType()
    {
        return messageHandler.MessageType();
    }
   
    @Override
    public Queue<Statement> GetMessageBody()
    {
        return messageHandler.GetBody();
    }
    
    
    public MessageHandler GetMessageHandler()
    {
        return messageHandler;
    }

    @Override
    protected boolean InternalEquals(NormalMessage other)
    {
        return this.messageHandler.equals(other.messageHandler);
    }

    @Override
    protected int InternalHashCode()
    {
        return messageHandler.hashCode();
    }
    
    @Override
    public String toString()
    {
        return messageHandler.GetID();
    }
    
    @Override
    public MessageParameters Parameters()
    {
        return messageHandler.Parameters();
    }

}
