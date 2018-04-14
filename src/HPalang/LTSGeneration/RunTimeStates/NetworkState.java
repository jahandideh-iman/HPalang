/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.RunTimeStates;

import HPalang.Core.CANSpecification;
import HPalang.Core.Message;
import HPalang.Core.MessagePacket;
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
    private final int bufferLimit;
    private final List<MessagePacket> buffer = new LinkedList<>();
    private boolean isIdle;
    private final CANSpecification canSpecifiation;

    public NetworkState(CANSpecification canSpecifiation, int bufferLimit)
    {
        this.bufferLimit = bufferLimit;
        this.canSpecifiation = canSpecifiation;
    }
    
    @Override
    protected NetworkState NewInstance()
    {
        return new NetworkState(canSpecifiation,bufferLimit);
    }

    @Override
    protected void CloneData(NetworkState copy)
    {
        copy.buffer.addAll(buffer);
        copy.SetIdle(isIdle);
    }

    @Override
    protected boolean DataEquals(NetworkState other)
    {
        return buffer.equals(other.buffer) &&
                this.isIdle == other.isIdle;
    }

    public void SetIdle(boolean idle)
    {
        this.isIdle = idle;
    }
    
    public boolean IsIdle()
    {
        return isIdle;
    }
    
    public void Buffer(MessagePacket packet)
    {
        if(buffer.size() >= bufferLimit)
            throw new RuntimeException("Buffer is full");
        buffer.add(packet);
    }
    
    public void Debuffer(MessagePacket networkPacket)
    {
        buffer.remove(networkPacket);
    } 
    
    public List<MessagePacket> Buffer()
    {
        return buffer;
    }

    public boolean HasPacketWithMessage(Message message)
    {
        for(MessagePacket packet : buffer)
            if(packet.Message().equals(message))
                return true;
        return false;
    }

    public CANSpecification CANSpecification()
    {
        return canSpecifiation;
    }

}
