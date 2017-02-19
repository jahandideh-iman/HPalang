/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Core;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Iman Jahandideh
 */
public class Actor
{
    private final String name;
    private final int capacity;
    private final Map<String,MessageHandler> messageHandlers = new HashMap<>();
    
    private final Map<DiscreteVariable, Integer> discreteVariables = new HashMap<>();

    public Actor(String name, int capacity)
    {
        this.name = name;
        this.capacity = capacity;
    }
    
    public void AddMessageHandler(String id,MessageHandler handler)
    {
        handler.SetID(id);
        messageHandlers.put(id,handler);
    }
     
    public void AddDiscreteVariable(DiscreteVariable var, int initialValue)
    {
        discreteVariables.put(var, initialValue);
    }
    
    public Map<DiscreteVariable, Integer> GetDiscreteVariables()
    {
        return discreteVariables;
    }
    
    public MessageHandler GetMessageHandler(String id)
    {
        return messageHandlers.get(id);
    }
    
    public int GetCapacity()
    {
        return capacity;
    }
    
    public String GetName()
    {
        return name;
    }
    
    public String GetDelayVariableName()
    {
        return name+"_delay";
    }
}
