/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Core.Messages;

import HPalang.Core.Equalitable;
import HPalang.Core.Message;
import HPalang.Core.MessageHandler;
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
    public Queue<Statement> GetMessageBody()
    {
        return messageHandler.GetBody();
    }
    
    @Override
    protected boolean InternalEquals(NormalMessage other)
    {
        return other.messageHandler.equals(this.messageHandler);
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
    
    public MessageHandler GetMessageHandler()
    {
        return messageHandler;
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
}
