/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Core;

import HPalang.Core.MessageHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Iman Jahandideh
 */
public class Actor
{
    private final String name;
    private final int capacity;
    private Map<String,MessageHandler> messageHandlers = new HashMap<>();

    public Actor(String name, int capacity)
    {
        this.name = name;
        this.capacity = capacity;
    }
    
    public void AddMessageHandler(String id,MessageHandler handler)
    {
        messageHandlers.put(id,handler);
    }
    
    public int GetCapacity()
    {
        return capacity;
    }
    
}
