/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.RunTimeStates;

import HPalang.Core.MessageArguments;
import HPalang.Core.SoftwareActor;
import HPalang.Core.MessagePacket;

import Mocks.EmptyMessage;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import static TestUtilities.NetworkingUtility.*;
import static TestUtilities.CoreUtility.*;
/**
 *
 * @author Iman Jahandideh
 */
public class NetworkStateTest
{
   
    @Test
    public void StatesWithEqualDataAreEqual()
    {
        SoftwareActor arbitraryActor = CreateSofwareActor("actor");
        MessagePacket packet = EmptySelfMessagePacketFor(arbitraryActor);
        
        NetworkState networkState1 = new NetworkState(5);
        NetworkState networkState2 = new NetworkState(5);
        
        networkState1.Buffer(packet);
        networkState1.SetIdle(true);
        
        networkState2.Buffer(packet);
        networkState2.SetIdle(true);
        
        assertThat(networkState1, equalTo(networkState2));
    }
    
    @Test
    public void HasTheBufferedPackets()
    {
        NetworkState networkState = new NetworkState(10);
        SoftwareActor actor1 = CreateSofwareActor("actor1");
        SoftwareActor actor2 = CreateSofwareActor("actor2");
        MessageArguments emptyArguments = new MessageArguments();
        
        MessagePacket packet1 = new MessagePacket(actor1, actor2,  new EmptyMessage("1to2"), emptyArguments);
        MessagePacket packet2 = new MessagePacket(actor1, actor2, new EmptyMessage("2to1"), emptyArguments);
        
        networkState.Buffer(packet1);
        networkState.Buffer(packet2);
        
        assertThat(networkState.Buffer(), hasItem(packet1));
        assertThat(networkState.Buffer(), hasItem(packet2));
    }
    
    @Test (expected = RuntimeException.class)
    public void RaisesErrorWhenBufferingIfBufferSizeIsMaxed()
    {
        int bufferSize = 1;
        NetworkState networkState = new NetworkState(bufferSize);
        SoftwareActor actor1 = CreateSofwareActor("actor1");
        SoftwareActor actor2 = CreateSofwareActor("actor2");
        MessageArguments emptyArguments = new MessageArguments();
        
        MessagePacket packet1 = new MessagePacket(actor1, actor2,  new EmptyMessage("1to2"), emptyArguments);
        MessagePacket packet2 = new MessagePacket(actor1, actor2, new EmptyMessage("2to1"), emptyArguments);
        
        networkState.Buffer(packet1);
        networkState.Buffer(packet2);
        
    }
    
}
