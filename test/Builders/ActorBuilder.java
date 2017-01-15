/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Builders;

import HPalang.Core.Actor;
import HPalang.Core.DiscreteVariable;
import HPalang.Core.MessageHandler;
import java.security.KeyPair;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Iman Jahandideh
 */
public class ActorBuilder
{
    
    private String id;
    private int capacity;
    private Map<String,MessageHandler> messageHandlers = new HashMap<>();
    private List<DiscreteVariable> discreteVariables = new LinkedList<>();
    
    public ActorBuilder WithID(String id)
    {
        this.id = id;
        return this;
    }
    
    public ActorBuilder WithDiscreteVariable(DiscreteVariable dVariable)
    {
        discreteVariables.add(dVariable);
        return this;
    }
    
    public ActorBuilder WithCapacity(int capacity)
    {
        this.capacity = capacity;
        return this;
    }
    
    public ActorBuilder WithHandler(String handlerId, MessageHandler handler)
    {
        messageHandlers.put(handlerId, handler);
        return this;
    }
    
    public Actor Build()
    {
        Actor actor = new Actor(id, capacity);
        for(Map.Entry<String,MessageHandler> entry :messageHandlers.entrySet())
            actor.AddMessageHandler(entry.getKey(), entry.getValue());
        
        return actor;
    }
}
