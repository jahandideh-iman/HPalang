/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration;

import HPalang.Core.Equalitable;
import HPalang.LTSGeneration.State;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Iman Jahandideh
 * @param <T>
 */
public abstract class CompositeStateT<T extends CompositeStateT> extends Equalitable<T> implements CompositeState
{
    private List<State> substates = new LinkedList<>();
        
    @Override
    public CompositeState AddSubstate(State substate)
    {
        substates.add(substate);
        OnSubstateAdded(substate);
        return this;
    }
    
    protected void OnSubstateAdded(State substate)
    {
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
    public final T DeepCopy()
    {
        T copy = NewInstance();
        CloneSubStates(copy);
        CloneData(copy);
        return copy;
    }
    
    abstract protected T NewInstance();
    
    private void CloneSubStates(T copy)
    {
        copy.InitSubstatesSize(copy.Substates().size());
        for(State substate : substates)
            copy.AddSubstate(substate.DeepCopy());
    }
    
    protected void InitSubstatesSize(int size)
    {
        substates = new ArrayList<>(size);
    }
    
    abstract protected void CloneData(T copy);


    @Override
    protected final boolean InternalEquals(T other)
    {
        return this.substates.equals(other.Substates())
                && DataEquals(other);
    }
    
    protected abstract boolean DataEquals(T other);

    @Override
    protected final int InternalHashCode()
    {
        int hash = 7;
        for(State state : substates)
            hash += state.hashCode();
        
        hash += DataHashCode();
        return hash;
    } 
    
    protected int DataHashCode()
    {
        return  0;
    }
}
