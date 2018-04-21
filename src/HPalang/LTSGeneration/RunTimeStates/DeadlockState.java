/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.RunTimeStates;

import HPalang.LTSGeneration.SimpleState;

/**
 *
 * @author Iman Jahandideh
 */
public class DeadlockState extends SimpleState<DeadlockState>
{

    private final String message;
    
    public DeadlockState()
    {
        this("");
    }
    
    public DeadlockState(String message)
    {
        this.message = message;
    }
    
    @Override
    protected DeadlockState NewInstance()
    {
        return new DeadlockState(message);
    }

    @Override
    protected boolean DataEquals(DeadlockState other)
    {
        return this.message.equals(other.message);
    }

    @Override
    protected void CloneData(DeadlockState copy)
    {
        
    }

    @Override
    protected int InternalHashCode()
    {
        return message.hashCode();
    }
    
    public String Message()
    {
        return message;
    }
}
