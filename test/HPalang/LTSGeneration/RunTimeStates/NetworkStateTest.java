/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.RunTimeStates;

import HPalang.Core.SoftwareActor;
import HPalang.Core.NetworkPacket;
import TestUtilities.Utilities;
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
        SoftwareActor actor1 = Utilities.NewActor("actor1");
        SoftwareActor actor2 = Utilities.NewActor("actor2");
        
        NetworkPacket packet1 = new NetworkPacket(actor1, new EmptyMessage("1to2"), actor2);
        NetworkPacket packet2 = new NetworkPacket(actor1, new EmptyMessage("2to1"), actor2);
        
        networkState.Buffer(packet1);
        networkState.Buffer(packet2);
        
        assertThat(networkState.Buffer(), hasItem(packet1));
        assertThat(networkState.Buffer(), hasItem(packet2));
    }
    
}
