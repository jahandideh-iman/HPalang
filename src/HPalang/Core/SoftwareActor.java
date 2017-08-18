/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Core;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Iman Jahandideh
 */
public class SoftwareActor extends Actor
{
    private final String name;
    private final int capacity;
    private final Map<String,MessageHandler> messageHandlers = new HashMap<>();
    
    private final Map<DiscreteVariable, Integer> discreteVariables = new HashMap<>();
    private final Map<ContinuousVariable, Float> continuousVariables = new HashMap<>();
    
    private ContinuousVariable delayVariable;
    
    private final SoftwareActorType type;
    
    
    public SoftwareActor(String name, int capacity)
    {
        this.name = name;
        this.capacity = capacity;
        delayVariable = new ContinuousVariable(name+"_delay");
        this.type = null;
    }

    public SoftwareActor(String name, SoftwareActorType type , int capacity)
    {
        this.name = name;
        this.type = type;
        this.capacity = capacity;
        delayVariable = new ContinuousVariable(name+"_delay");
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
    
    public void AddContinuousVariable(ContinuousVariable var, float initialValue)
    {
        continuousVariables.put(var, initialValue);
    }
    
    public Map<DiscreteVariable, Integer> GetDiscreteVariables()
    {
        return discreteVariables;
    }
    
    public Collection<ContinuousVariable> GetContinuousVariables()
    {
        return continuousVariables.keySet();
    }
    
    public MessageHandler GetMessageHandler(String id)
    {
        return messageHandlers.get(id);
    }
    
    public Collection<MessageHandler> GetMessageHandlers()
    {
        return messageHandlers.values();
    }
    
    public int GetCapacity()
    {
        return capacity;
    }
    
    public String GetName()
    {
        return name;
    }
    
    public ContinuousVariable GetDelayVariable()
    {
        return delayVariable;
    }

    public DiscreteVariable FindDiscreteVariable(String name)
    {
        for(DiscreteVariable var : discreteVariables.keySet())
            if(var.Name().equals(name))
                return var;
        return null;
    }
    
    public ContinuousVariable FindContinuousVariable(String name)
    {
        for(ContinuousVariable var : continuousVariables.keySet())
            if(var.Name().equals(name))
                return var;
        return null;
    }
    
    public Variable FindVariable(String name)
    {
        Variable var = FindDiscreteVariable(name);
        if(var == null)
            var = FindContinuousVariable(name);
        return var;
    }
    
    public boolean HasDiscreteVariable(String name)
    {
        return FindDiscreteVariable(name) != null;
    }
    
    public boolean HasContinuousVariable(String name)
    {
        return FindContinuousVariable(name) != null;
    }

    public SoftwareActorType Type()
    {
        return type;
    }
}
