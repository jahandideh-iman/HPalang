/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.RunTimeStates;

import HPalang.Core.ContinuousVariablePool;
import HPalang.LTSGeneration.SimpleState;

/**
 *
 * @author Iman Jahandideh
 */
public class VariablePoolState extends SimpleState<VariablePoolState>
{
    private ContinuousVariablePool pool;

    public VariablePoolState(ContinuousVariablePool pool)
    {
        this.pool = pool;
    }

    public ContinuousVariablePool Pool()
    {
        return pool;
    }

    @Override
    protected VariablePoolState NewInstance()
    {
        return new VariablePoolState(new ContinuousVariablePool(pool));
    }

    @Override
    protected boolean DataEquals(VariablePoolState other)
    {
        return pool.equals(other.pool);
    }

    @Override
    protected void CloneData(VariablePoolState copy)
    {
    }

    @Override
    protected int InternalHashCode()
    {
        return 0;
    }
}
