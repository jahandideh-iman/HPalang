/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Core;

import TestUtilities.Utilities;
import Mocks.EmptyMessage;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;


/**
 *
 * @author Iman Jahandideh
 */
public class NetworkPacketTest
{
    @Test
    public void PacketsWithEqualDataAreEqual()
    {
        SoftwareActor sender = Utilities.NewActor("sender");
        Message message = new EmptyMessage("Message");
        SoftwareActor receiver = Utilities.NewActor("receiver");
        
        NetworkPacket packet = new NetworkPacket(sender, message, receiver);
        
        assertThat(packet, is(equalTo(new NetworkPacket(sender, message, receiver))));
    }
    
}
