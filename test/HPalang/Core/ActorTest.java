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
    public void HasTheBoundedInstances()
    {
        ActorType type = CreateType("Type");
        Actor actor = new Actor("actor", type);

        ActorType instanceType = CreateType("InstanceType");
        Actor instance = new Actor("instance",  instanceType);
        InstanceParameter instanceParameter = new InstanceParameter("param", instanceType);
        
        type.AddInstanceParameter(instanceParameter);
        
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
        MessageHandler delegationMessageHandler = CreateMessageHandler();
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
    
    private MessageHandler CreateMessageHandler()
    {
        return new MessageHandler(Message.MessageType.Data);
    }
}
