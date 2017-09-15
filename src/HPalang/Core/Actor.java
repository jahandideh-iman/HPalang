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


public class Actor<T extends ActorType>
{ 
    private final T type; 
    
    private final String name;
    private final int queueCapacity;
    
    
    private final Map<Actor, CommunicationType> communicationTypes = new HashMap<>();
    
    private final Map<Pair<Actor, Message>, Float > networkDelays = new HashMap<>();
    private final Map<MessageHandler, Integer> messageHandlersPriority = new HashMap<>();
    private final Map<InstanceParameter, Actor> instanceArguments = new HashMap<>();
    private final Map<DelegationParameter, Delegation> delegationArguments = new HashMap<>();
       
    public Actor(String name, T type)
    {
        this(name, type, 0);
    }
        
    public Actor(String name, T type, int queueCapacity)
    {
        this.name = name;
        this.type = type;
        this.queueCapacity = queueCapacity;
    }
    
    public T Type()
    {
        return type;
    }
    public String Name()
    {
        return name;
    }
    
    public int QueueCapacity()
    {
        return queueCapacity;
    }
    
    public void BindInstance(InstanceParameter parameter, Actor instance, CommunicationType communicationType)
    {
        assert (Type().HasInstanceParameter(parameter));
        assert (parameter.Type().equals(instance.Type()));
        
        instanceArguments.put(parameter, instance);
        SetCommunicationType(instance, communicationType);
    }
    
    public Actor GetInstanceFor(InstanceParameter parameter)
    {
        return instanceArguments.get(parameter);
    }

    public void BindDelegation(DelegationParameter parameter, Delegation delegation, CommunicationType communicationType)
    {
        assert (Type().HasDelegationParameter(parameter));
        delegationArguments.put(parameter, delegation);
        SetCommunicationType(delegation.Actor(), communicationType);
    }
    
    public Delegation GetDelegationFor(DelegationParameter parameter)
    {
        return delegationArguments.get(parameter);
    }
    
    
    // Warning: This is for testing. Use BindInstance instead.
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

    public void SetMessageHandlerPriority(MessageHandler handler, int priority)
    {
        assert (type.FindMessageHandler(handler.GetID()) != null);
        messageHandlersPriority.put(handler, priority);
    }
}
