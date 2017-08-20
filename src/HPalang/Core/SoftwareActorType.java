/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Core;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Iman Jahandideh
 */
public class SoftwareActorType extends ActorType
{
    private final String name;
    
    private final Map<String,DiscreteVariable> variables = new HashMap<>();
    private final Map<String,MessageHandler> messageHandlers = new HashMap<>();
    
    public SoftwareActorType(String name)
    {
        this.name = name;
    }
    
    public void AddMessageHandler(String id, MessageHandler handler)
    {
        handler.SetID(id);
        messageHandlers.put(id,handler);
    }
     
    public void AddVariable(DiscreteVariable var)
    {
        variables.put(var.Name(),var);
    }

    public MessageHandler FindMessageHandler(String messageHandlerName)
    {
        return messageHandlers.get(messageHandlerName);
    }

    public DiscreteVariable FindVariable(String variableName)
    {
        return variables.get(variableName);
    }
    
    public boolean HasVariable(String name)
    {
        return FindVariable(name) != null;
    }

    public Collection<DiscreteVariable> Variables()
    {
        return variables.values();
    }
}
