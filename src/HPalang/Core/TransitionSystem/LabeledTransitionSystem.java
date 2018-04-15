/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Core.TransitionSystem;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Iman Jahandideh
 */
public class LabeledTransitionSystem<T>
{    
    private static final boolean APPLY_MEMORY_OPTIMIZATION = true;
    private LTSState<T> initialState = null;
        
        
    private final Map<T,LTSState<T>> states = new HashMap<>();
    
    private final Set<Transition<T>> transitions = new HashSet<>();
    
    //private Map<T, List<Transition<T>>> outTransitionsCache = new HashMap<>();
    //private Map<T, List<Transition<T>>> inTransitionsCache = new HashMap<>();
    
    public void SetInitialState(T state)
    {
        initialState = TryAddState(state);;
    }
    
    public LTSState<T> InitialState()
    {
        return initialState;
    }
    
    // TODO: Rename this crap.
    public LTSState<T> TryAddState(T state)
    {
        LTSState<T> ltsState  = states.getOrDefault(state, null);
        
        if(ltsState == null)
        {
            ltsState = new SimpleLTSState<>(state);
            states.put(state, ltsState);
        }
        return ltsState;
    }
    
    public boolean HasState(T state)
    {
        return states.containsKey(state);
    }
    
    public Collection<LTSState<T>> LTSStates()
    {
        return states.values();
    }
    
    public Collection<T> States()
    {
        return states.keySet();
    }
    
    public void AddTransition(LTSState<T> origin,Label label, LTSState<T> destination)
    {
        Transition<T> transtion = new Transition<>(origin,label,destination);
        
        if(APPLY_MEMORY_OPTIMIZATION)
        {
            if(transitions.contains(transtion))
                return;
        }
        transtion.GetOrign().AddOutTransition(transtion);
        transtion.GetDestination().AddInTransition(transtion);
        transitions.add(transtion);
    }
    
    
    public Set<Transition<T>> Transitions()
    {
        return transitions;
    }
    
    public boolean HasTransition(LTSState origin,Label label, LTSState destination)
    {
        return transitions.contains(new Transition(origin, label, destination));
    }  
    
    
    public void RemoveTranstion(Transition t)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        //transitions.remove(t);
    }


    public int TransitionsSize()
    {
        return transitions.size();
    }
}
