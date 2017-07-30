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
public class NetworkPacket extends Equalitable<NetworkPacket>
{
    private final Actor sender;
    private final Message message;
    private final Actor receiver;
    
    public NetworkPacket(Actor sender, Message message, Actor receiver)
    {
        this.sender = sender;
        this.message = message;
        this.receiver = receiver;
    }
    
    public Actor Receiver()
    {
        return receiver;
    }

    public Message Message()
    {
        return message;
    }
    
    @Override
    protected boolean InternalEquals(NetworkPacket other)
    {
        return sender.equals(other.sender) && 
                message.equals(other.message) &&
                receiver.equals(other.receiver);
                
    }

    @Override
    protected int InternalHashCode()
    {
        return sender.hashCode() + message.hashCode() + receiver.hashCode();
    }   
}
