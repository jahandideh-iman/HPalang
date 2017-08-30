/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Core;

/**
 *
 * @author Iman Jahandideh
 */
public class MessagePacket extends Equalitable<MessagePacket>
{
    private final Actor sender;
    private final SoftwareActor receiver;
    private final Message message;
    private final MessageArguments arguments;

    public MessagePacket(Actor sender, SoftwareActor receiver, Message message, MessageArguments arguments)
    {
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
        this.arguments = arguments;
    }
    
    public SoftwareActor Receiver()
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
    
    @Override
    protected boolean InternalEquals(MessagePacket other)
    {
        return sender.equals(other.sender) &&
                receiver.equals(other.receiver) &&
                message.equals(other.message) &&
                arguments.equals(other.arguments);
                
    }

    @Override
    protected int InternalHashCode()
    {
        return 0;
    }   
   
}
