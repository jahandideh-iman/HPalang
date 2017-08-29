/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.RunTimeStates;

import HPalang.Core.MessageArguments;
import HPalang.Core.SoftwareActor;
import HPalang.Core.MessagePacket;
import TestUtilities.CoreUtility;
import Mocks.EmptyMessage;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

/**
 *
 * @author Iman Jahandideh
 */
public class NetworkStateTest
{
   
    @Test
    public void HasTheBufferedPackets()
    {
        NetworkState networkState = new NetworkState();
        SoftwareActor actor1 = CoreUtility.CreateSofwareActor("actor1");
        SoftwareActor actor2 = CoreUtility.CreateSofwareActor("actor2");
        MessageArguments emptyArguments = new MessageArguments();
        
        MessagePacket packet1 = new MessagePacket(actor1, actor2,  new EmptyMessage("1to2"), emptyArguments);
        MessagePacket packet2 = new MessagePacket(actor1, actor2, new EmptyMessage("2to1"), emptyArguments);
        
        networkState.Buffer(packet1);
        networkState.Buffer(packet2);
        
        assertThat(networkState.Buffer(), hasItem(packet1));
        assertThat(networkState.Buffer(), hasItem(packet2));
    }
    
}
