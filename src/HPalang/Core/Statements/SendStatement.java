/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Core.Statements;

import HPalang.Core.Statement;
import HPalang.Core.Actor;
import HPalang.Core.MessageHandler;
import HPalang.Core.Message;

/**
 *
 * @author Iman Jahandideh
 */
public class SendStatement extends AbstractStatement<SendStatement>
{
    private final Actor receiver;
    private final Message message;
    
    public SendStatement(Actor receiver, Message message)
    {
        this.receiver = receiver;
        this.message = message;
    }
    
    public Actor GetReceiver()
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
