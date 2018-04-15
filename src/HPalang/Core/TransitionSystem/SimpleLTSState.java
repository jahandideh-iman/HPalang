/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Core.TransitionSystem;

import HPalang.Core.Equalitable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Iman Jahandideh
 */
public class SimpleLTSState<T> extends  Equalitable<SimpleLTSState<T>> implements LTSState<T>
{
    private final List<Transition<T>> outTransitions = new ArrayList<>();
    private final List<Transition<T>> inTransitions = new ArrayList<>();
    
    private final T innerState;
    
    public SimpleLTSState(T innerState)
    {
        this.innerState = innerState;
    }
    
    @Override
    public List<Transition<T>> OutTransitions()
    {
        return outTransitions;
    }

    @Override
    public List<Transition<T>> InTransitions()
    {
        return inTransitions;
    }

    @Override
    public void AddInTransition(Transition<T> tr)
    {
        inTransitions.add(tr);
    }

    @Override
    public void AddOutTransition(Transition<T> tr)
    {
        outTransitions.add(tr);
    }

    @Override
    public T InnerState()
    {
        return innerState;
    }

    @Override
    protected boolean InternalEquals(SimpleLTSState<T> other)
    {
        // WARNING: What about the transitions.
        return this.innerState.equals(other.innerState);
    }

    @Override
    protected int InternalHashCode()
    {
        return innerState.hashCode();
    }

}
