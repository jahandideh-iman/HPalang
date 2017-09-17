/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Core;

import HPalang.Core.Variables.RealVariable;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Iman Jahandideh
 */
public class MessagePacket extends Equalitable<MessagePacket>
{
    private final Actor sender;
    private final Actor receiver;
    private final Message message;
    private final MessageArguments arguments;
    
    // TODO: Remove this crap.
    private final List<RealVariable> pooledVaraibles;

    public MessagePacket(Actor sender, Actor receiver, Message message, MessageArguments arguments)
    {
        this(sender,receiver,message,arguments, Collections.EMPTY_LIST);
    }

    public MessagePacket(Actor sender, Actor receiver, Message message, MessageArguments arguments, List<RealVariable> pooledVariables)
    {
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
        this.arguments = arguments;
        this.pooledVaraibles = pooledVariables;
    }
    
    public Actor Receiver()
    {
        return receiver;
    }
    
    public Actor Sender()
    {
        return sender;
    }

    public Message Message()
    {
        return message;
    }
    
    public MessageArguments Arguments()
    {
        return arguments;
    }
    
    public List<RealVariable> PooledVariables()
    {
        return pooledVaraibles;
    }
    
    @Override
    protected boolean InternalEquals(MessagePacket other)
    {
        return sender.equals(other.sender) &&
                receiver.equals(other.receiver) &&
                message.equals(other.message) &&
                arguments.equals(other.arguments) &&
                pooledVaraibles.equals(other.pooledVaraibles);
                
    }

    @Override
    protected int InternalHashCode()
    {
        return 0;
    }   
   
}
