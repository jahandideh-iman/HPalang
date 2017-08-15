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
    private final SoftwareActor sender;
    private final Message message;
    private final SoftwareActor receiver;
    
    public NetworkPacket(SoftwareActor sender, Message message, SoftwareActor receiver)
    {
        this.sender = sender;
        this.message = message;
        this.receiver = receiver;
    }
    
    public SoftwareActor Receiver()
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
