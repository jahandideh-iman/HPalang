/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Core;

import HPalang.Core.Messages.NormalMessage;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Iman Jahandideh
 */
public class CANSpecification
{

    private static class PriorityPacket extends Equalitable<PriorityPacket>
    {
        private final Actor actor;
        private final Message message;
        public PriorityPacket(Actor actor, Message message)
        {
            this.actor = actor;
            this.message = message;
        }
        @Override
        protected boolean InternalEquals(PriorityPacket other)
        {
            return this.actor.equals(other.actor) && this.message.equals(other.message);
        }
        @Override
        protected int InternalHashCode()
        {
            return 1;
        }
    }
    
    private static class DelayPacket extends Equalitable<DelayPacket>
    {
        private final Actor sender;
        private final Actor reciever;
        private final Message message;
        
        public DelayPacket(Actor sender, Actor reciever, Message message)
        {
            this.sender = sender;
            this.reciever = reciever;
            this.message = message;
        }
        
        @Override
        protected boolean InternalEquals(DelayPacket other)
        {
            return this.sender.equals(other.sender) && this.reciever.equals(other.reciever) && this.message.equals(other.message);
        }
        @Override
        protected int InternalHashCode()
        {
            return 1;
        }
    }
    
    private final Map<PriorityPacket, Integer> networkPriorities = new HashMap<>();
    private final Map<DelayPacket, Float> networkDelays = new HashMap<>();
    
    public void SetNetworkPriority(Actor actor, Message message, int priority)
    {
        networkPriorities.put(new PriorityPacket(actor, message), priority);
    }
    
    public int NetworkPriorityFor(Actor actor, Message message)
    {
        return networkPriorities.get(new PriorityPacket(actor, message));
    }
    
    public boolean HasNetworkPriorityFor(Actor actor, Message message)
    {
        return networkPriorities.containsKey(new PriorityPacket(actor, message));
    }
    
    public void SetNetworkDelay(Actor sender, Actor reciever, Message message, float delay)
    {
        networkDelays.put(new DelayPacket(sender, reciever, message), delay);
    }
    
    public float NetworkDelayFor(Actor sender, Actor reciever, Message message)
    {
        return networkDelays.get(new DelayPacket(sender, reciever, message));
    }

}
