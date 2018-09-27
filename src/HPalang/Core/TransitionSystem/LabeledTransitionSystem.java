/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Core.TransitionSystem;

import HPalang.LTSGeneration.RunTimeStates.GlobalRunTimeState;
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
    private StateWrapper<T> initialState = null;
        
        
    private final Map<T,StateWrapper<T>> states = new HashMap<>();
    
    private final Set<Transition<T>> transitions = new HashSet<>();
    
    //private Map<T, List<Transition<T>>> outTransitionsCache = new HashMap<>();
    //private Map<T, List<Transition<T>>> inTransitionsCache = new HashMap<>();
    
    public void SetInitialState(T state)
    {
        initialState = TryAddState(state);
    }
    
    public StateWrapper<T> InitialState()
    {
        return initialState;
    }
    
    // TODO: Rename this crap.
    public StateWrapper<T> TryAddState(T state)
    {
        StateWrapper<T> ltsState  = states.getOrDefault(state, null);
        
        if(ltsState == null)
        {
            ltsState = new SimpleLTSState<>(state);
            states.put(state, ltsState);
        }
        return ltsState;
    }
    
    public StateWrapper<T> WrapperFor(T state)
    {
        StateWrapper<T> ltsState  = states.getOrDefault(state, null);
        return ltsState;
    }
    
    public boolean HasState(T state)
    {
        return states.containsKey(state);
    }
    
    public Collection<StateWrapper<T>> StateWrappers()
    {
        return states.values();
    }
    
    public Collection<T> States()
    {
        return states.keySet();
    }
    
    public void AddTransition(StateWrapper<T> origin,Label label, StateWrapper<T> destination)
    {
        Transition<T> transtion = new Transition<>(origin,label,destination);
        
        if(APPLY_MEMORY_OPTIMIZATION)
        {
            if(transitions.contains(transtion))
                return;
//            if(transtion.GetOrign().OutTransitions().contains(transtion))
//                return;
        }
        transtion.GetOrign().AddOutTransition(transtion);
        transtion.GetDestination().AddInTransition(transtion);
        transitions.add(transtion);
    }
    
    
    public Set<Transition<T>> Transitions()
    {
        return transitions;
    }
    
    public boolean HasTransition(StateWrapper origin,Label label, StateWrapper destination)
    {
        return transitions.contains(new Transition(origin, label, destination));
    }  
    
    
    public void RemoveTranstion(Transition t)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    public int TransitionsSize()
    {
        return transitions.size();
    }

    public void RemoveState(StateWrapper<T> state)
    {
        // TODO: Check for initial state.
        
        state.OutTransitions().forEach(t -> transitions.remove(t));
        state.OutTransitions().forEach(t -> t.GetDestination().RemoveInTransition(t));
        state.InTransitions().forEach(t -> transitions.remove(t));
        state.InTransitions().forEach(t -> t.GetOrign().RemoveOutTransition(t));
        
        states.remove(state.InnerState());
    }
}
