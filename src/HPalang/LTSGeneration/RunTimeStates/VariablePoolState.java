/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.RunTimeStates;

import HPalang.Core.NullRealVariablePool;
import HPalang.Core.RealVariablePool;
import HPalang.LTSGeneration.SimpleState;

/**
 *
 * @author Iman Jahandideh
 */
public class VariablePoolState extends SimpleState<VariablePoolState>
{
    private RealVariablePool pool;

    public VariablePoolState()
    {
        pool = new NullRealVariablePool();
    }
        
    public VariablePoolState(RealVariablePool pool)
    {
        this.pool = pool;
    }

    public RealVariablePool Pool()
    {
        return pool;
    }
    
    // WARNING: This is for testing only.
    // TODO: Remove this method.
    public void SetPool(RealVariablePool pool)
    {
        this.pool = pool;
    }

    @Override
    protected VariablePoolState NewInstance()
    {
        return new VariablePoolState();
    }

    @Override
    protected boolean DataEquals(VariablePoolState other)
    {
        return pool.equals(other.pool);
    }

    @Override
    protected void CloneData(VariablePoolState copy)
    {
        copy.pool = pool.DeepCopy();
    }

    @Override
    protected int InternalHashCode()
    {
        return 0;
    }
}
