/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Core.Statements;

import HPalang.Core.Statement;
import HPalang.Core.SoftwareActor;
import HPalang.Core.MessageHandler;
import HPalang.Core.Message;

/**
 *
 * @author Iman Jahandideh
 */
public class SendStatement extends AbstractStatement<SendStatement>
{
    private final SoftwareActor receiver;
    private final Message message;
    private final ReceiverLocator receiverLocator;
    
    public interface ReceiverLocator
    {
        SoftwareActor GetActor();
    }
    
    public SendStatement(ReceiverLocator receiverLocator, Message message)
    {
        this.receiverLocator = receiverLocator;
        this.message = message;
        this.receiver = null;
    }   
    
    public SendStatement(SoftwareActor receiver, Message message)
    {
        this.receiver = receiver;
        this.message = message;
        this.receiverLocator = null;
    }
    
    public SoftwareActor GetReceiver()
    {
        return receiver;
    }
    
    public Message GetMessage()
    {
        return message;
    }
    
    @Override
    protected boolean InternalEquals(SendStatement other)
    {
        return this.receiver.equals(other.receiver)
                && this.message.equals(other.message);
    }

    @Override
    protected int InternalHashCode()
    {
        return 1;
    }
    
    @Override
    public String toString()
    {
        return "(" + receiver.GetName() + "!" + message.toString() + ")";
    }
}
