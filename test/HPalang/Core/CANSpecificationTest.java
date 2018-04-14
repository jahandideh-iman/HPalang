/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Core;

import Mocks.EmptyMessage;
import TestUtilities.CoreUtility;
import static TestUtilities.CoreUtility.CreateSofwareActor;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Iman Jahandideh
 */
public class CANSpecificationTest
{
    CANSpecification canSpecification = new CANSpecification();
    
    Actor actor1 = CreateSofwareActor("actor1");
    Actor actor2 = CreateSofwareActor("actor2");

    Message message1 = new EmptyMessage("message1");  
    Message message2 = new EmptyMessage("message2");

    @Test
    public void HasTheAddedNetworkPriorities()
    {
        canSpecification.SetNetworkPriority(actor1, message1 , 1);
        canSpecification.SetNetworkPriority(actor2, message2 , 2);
        
        assertThat(canSpecification.NetworkPriorityFor(actor1,message1), is(equalTo(1))); 
        assertThat(canSpecification.NetworkPriorityFor(actor2,message2), is(equalTo(2))); 
        
        assertThat(canSpecification.HasNetworkPriorityFor(actor1,message1), is(equalTo(true)));
        assertThat(canSpecification.HasNetworkPriorityFor(actor2,message2), is(equalTo(true)));
    }
    
    @Test
    public void HasTheAddedNetworkDelays()
    {

        canSpecification.SetNetworkDelay(actor1, actor2, message1 , 1.0f);
        canSpecification.SetNetworkDelay(actor2, actor1, message2 , 2.0f);
        
        assertThat(canSpecification.NetworkDelayFor(actor1, actor2, message1), is(equalTo(1.0f))); 
        assertThat(canSpecification.NetworkDelayFor(actor2, actor1 ,message2), is(equalTo(2.0f))); 
        
    }
    
}
