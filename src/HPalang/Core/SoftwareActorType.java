/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Core;

import HPalang.Core.Variable.Type;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Iman Jahandideh
 */
public class SoftwareActorType extends ActorType
{
    private final Map<String,MessageHandler> messageHandlers = new HashMap<>();
    
    public SoftwareActorType(String name)
    {
        super(name, Arrays.asList(Type.integer,Type.integer));
    }
    
    public void AddMessageHandler(String id, MessageHandler handler)
    {
        handler.SetID(id);
        messageHandlers.put(id,handler);
    }
     


    public MessageHandler FindMessageHandler(String messageHandlerName)
    {
        return messageHandlers.get(messageHandlerName);
    }

}
