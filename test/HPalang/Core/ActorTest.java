/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Core;

import static org.hamcrest.CoreMatchers.equalTo;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Iman Jahandideh
 */
public class ActorTest
{
 
    @Test
    public void HasTheAddedCommunication()
    {
        Actor actor1 = new Actor("actor1");
        Actor actor2 = new Actor("actor2");
        
        actor1.SetCommunicationType(actor2, CommunicationType.CAN);
        
        assertThat(actor1.CommunicationTypeFor(actor2), equalTo(CommunicationType.CAN));
    }
    
}
