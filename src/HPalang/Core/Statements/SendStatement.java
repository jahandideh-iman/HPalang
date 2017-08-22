/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Core.Statements;

import HPalang.Core.ActorLocator;
import HPalang.Core.SoftwareActor;
import HPalang.Core.Message;

/**
 *
 * @author Iman Jahandideh
 */
public class SendStatement extends AbstractStatement<SendStatement>
{
    private final Message message;
    private final ActorLocator receiverLocator;
    
    public SendStatement(ActorLocator receiverLocator, Message message)
    {
        this.receiverLocator = receiverLocator;
        this.message = message;
    }   
//    
//    public SendStatement(SoftwareActor receiver, Message message)
//    {
//        this.receiver = receiver;
//        this.message = message;
//        this.receiverLocator = null;
//    }
    
    public SoftwareActor GetReceiver()
    {
        return (SoftwareActor) receiverLocator.GetActor();
    }
    
    public Message GetMessage()
    {
        return message;
    }
    
    @Override
    protected boolean InternalEquals(SendStatement other)
    {
        return this.receiverLocator.equals(other.receiverLocator)
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
        return "(" + receiverLocator.GetActor().Name() + "!" + message.toString() + ")";
    }
}
