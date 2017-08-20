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
    private final SoftwareActorType type;
        
    private final int capacity;
    
    private final Map<DiscreteVariable, Integer> initialValues = new HashMap<>();
 
    public SoftwareActor(String name, int capacity)
    {
        this(name, null, capacity);
    }

    public SoftwareActor(String name, SoftwareActorType type , int capacity)
    {
        this.name = name;
        this.type = type;
        this.capacity = capacity;
    }
    
    public SoftwareActor(String name, SoftwareActorType type)
    {
        this(name, type, 5);
    }
  
    public Map<DiscreteVariable, Integer> InitialValues()
    {
        return initialValues;
    }
      
    public int Capacity()
    {
        return capacity;
    }
    
    public String Name()
    {
        return name;
    }
    
    public SoftwareActorType Type()
    {
        return type;
    }
}
