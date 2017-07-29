/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Core;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Iman Jahandideh
 * @param <T>
 */
public abstract class StateT<T extends State> extends Equalitable<T> implements State
{
    private final List<State> substates = new LinkedList<>();
        
    @Override
    public void AddSubstate(State substate)
    {
        substates.add(substate);
    }

    @Override
    public <T extends State> T FindSubState(Class<T> clazz)
    {
        for(State subState : substates)
            if(clazz.equals(subState.getClass()))
                return (T) subState;
        return null;
    }

    @Override
    public <T extends State> Collection<T> FindSubStates(Class<T> clazz)
    {
        List<T> result = new LinkedList<>();
        
        for(State subState : substates)
            if(clazz.equals(subState.getClass()))
                result.add((T) subState);
        
        return result;
    }

    @Override
    public Collection<State> Substates()
    {
        return substates;
    }
    
    @Override
    public State DeepCopy()
    {
        T copy = NewInstance();
        CloneSubStates(copy);
        CloneData(copy);
        return copy;
    }
    
    abstract protected T NewInstance();
    
    private void CloneSubStates(T copy)
    {
        for(State substate : substates)
            copy.AddSubstate(substate.DeepCopy());
    }
    
    abstract protected void CloneData(T copy);


    @Override
    protected boolean InternalEquals(T other)
    {
        return this.substates.equals(other.Substates())
                && DataEquals(other);
    }
    
    protected abstract boolean DataEquals(T other);

    @Override
    protected int InternalHashCode()
    {
        int hash = 7;
        for(State state : substates)
            hash += state.hashCode();
        
        return hash;
    } 
}
