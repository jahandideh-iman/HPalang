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

    @Override
    protected DeadlockState NewInstance()
    {
        return new DeadlockState();
    }

    @Override
    protected boolean DataEquals(DeadlockState other)
    {
        return true;
    }

    @Override
    protected void CloneData(DeadlockState copy)
    {
        
    }

    @Override
    protected int InternalHashCode()
    {
        return 13;
    }
    
}
