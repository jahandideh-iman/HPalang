/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Core;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Iman Jahandideh
 */


public class Actor
{ 
    private final String name;
    
    private final Map<Actor, CommunicationType> communicationTypes = new HashMap<>();
    
    private final Map<Pair<Actor, Message>, Float > networkDelays = new HashMap<>();
        
    public Actor(String name)
    {
        this.name = name;
    }
    
    public String Name()
    {
        return name;
    }
    
    public void BindInstance(InstanceParameter parameter, Actor instance)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void BindDelegation(DelegationParameter parameter, Delegation delegation)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    public void SetCommunicationType(Actor actor, CommunicationType communicationType)
    {
        communicationTypes.put(actor, communicationType);
    }
    
    public CommunicationType CommunicationTypeFor(Actor actor)
    {
        if(communicationTypes.containsKey(actor))
            return communicationTypes.get(actor);
        return CommunicationType.Invalid;
    }
    
    public void SetNetworkDelay(Actor receiver, Message message, float delay)
    {
        Pair<Actor, Message> pair = new Pair<>(receiver,message);
        
        networkDelays.put(pair, delay);
    }
    
    public float NetworkDelayFor(Message message, Actor receiver)
    {
        Pair<Actor, Message> pair = new Pair<>(receiver,message);
        return networkDelays.get(pair);
    }
    

}
