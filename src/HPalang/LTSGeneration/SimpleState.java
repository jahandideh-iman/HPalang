/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration;

import HPalang.Core.Equalitable;

/**
 *
 * @author Iman Jahandideh
 */
public abstract class SimpleState<T extends SimpleState> extends Equalitable<T> implements State
{
    @Override
    public final T DeepCopy()
    {
        T copy = NewInstance();
        CloneData(copy);
        return copy;
    }
    
    @Override
    protected final boolean InternalEquals(T other)
    {
        return DataEquals(other);
    }
    
    abstract protected boolean DataEquals(T other);
    abstract protected T NewInstance();
    abstract protected void CloneData(T copy);
}
