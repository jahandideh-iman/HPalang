/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.RunTimeStates;

import HPalang.Core.NetworkPacket;
import HPalang.LTSGeneration.CompositeStateT;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Iman Jahandideh
 */
public class NetworkState extends CompositeStateT<NetworkState>
{
    private final List<NetworkPacket> buffer = new LinkedList<>();
    
    @Override
    protected NetworkState NewInstance()
    {
        return new NetworkState();
    }

    @Override
    protected void CloneData(NetworkState copy)
    {
        copy.buffer.addAll(buffer);
    }

    @Override
    protected boolean DataEquals(NetworkState other)
    {
        return buffer.equals(other.buffer);
    }

    public void Buffer(NetworkPacket packet)
    {
        buffer.add(packet);
    }
    
    public void Debuffer(NetworkPacket networkPacket)
    {
        buffer.remove(networkPacket);
    } 
    
    public Collection<NetworkPacket> Buffer()
    {
        return buffer;
    }

}
