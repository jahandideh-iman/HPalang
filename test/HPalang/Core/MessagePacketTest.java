/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Core;

import TestUtilities.CoreUtility;
import Mocks.EmptyMessage;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;


/**
 *
 * @author Iman Jahandideh
 */
public class MessagePacketTest
{
    @Test
    public void PacketsWithEqualDataAreEqual()
    {
        SoftwareActor sender = CoreUtility.CreateSofwareActor("sender");
        SoftwareActor receiver = CoreUtility.CreateSofwareActor("receiver");
        Message message = new EmptyMessage("Message");
        MessageArguments arguments = new MessageArguments();
        int priority = 2;

        
        MessagePacket packet1 = new MessagePacket(sender, receiver, message, arguments, priority);
        MessagePacket packet2 = new MessagePacket(sender, receiver, message, arguments, priority);
        
        assertThat(packet1, equalTo(packet2));
    }
    
}
