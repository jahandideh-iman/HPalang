/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Core;

import Mocks.EmptyMessage;
import java.util.Collections;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Iman Jahandideh
 */
public class ActorTest
{
 
    class TestActorType extends ActorType
    {

        public TestActorType(String name)
        {
            super(name, Collections.EMPTY_LIST);
        }
    }
    
    @Test
    public void HasTheAddedNetworkDelay()
    {
        Actor sender = new Actor("sender",null);
        Actor receiver = new Actor("receiver",null);
        
        Message message = new EmptyMessage();
        
        sender.SetNetworkDelay(receiver, message, 2f);
        
        assertThat(sender.NetworkDelayFor(message, receiver), equalTo(2f));
    }
    
    @Test
    public void HasTheBoundedInstances()
    {
        InstanceParameter instanceParameter = new InstanceParameter("param", CreateEmptyType());
        
        ActorType type = CreateType("Type");
        type.AddInstanceParameter(instanceParameter);
        
        Actor actor = new Actor("actor", type);
        Actor instance = new Actor("instance",  CreateEmptyType());
        
        actor.BindInstance(instanceParameter, instance, CommunicationType.CAN);
        
        assertThat(actor.GetInstanceFor(instanceParameter), is(instance));
        assertThat(actor.CommunicationTypeFor(instance), is(CommunicationType.CAN));
    }
    
    @Test
    public void HasTheBoundedDelegations()
    {
        DelegationParameter delegationParameter = new DelegationParameter("param");
        ActorType type = CreateType("Type");
        type.AddDelegationParameter(delegationParameter);
        
        Actor actor = new Actor("actor", type);
        Actor delegationInstance = new Actor("instance",  CreateEmptyType());
        MessageHandler delegationMessageHandler = new MessageHandler();
        Delegation delagation = new Delegation(delegationInstance, delegationMessageHandler);
        
        actor.BindDelegation(delegationParameter, delagation, CommunicationType.CAN);
        
        assertThat(actor.GetDelegationFor(delegationParameter), is(delagation));
        assertThat(actor.CommunicationTypeFor(delagation.Actor()), is(CommunicationType.CAN));
    }
    
    private ActorType CreateEmptyType()
    {
        return CreateType("empty");
    }
    
    private ActorType CreateType(String name)
    {
        return new TestActorType(name);
    }
}
