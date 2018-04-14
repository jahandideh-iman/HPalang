/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration;

import HPalang.LTSGeneration.RunTimeStates.GlobalRunTimeState;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Iman Jahandideh
 */
public class LabeledTransitionSystem
{    
    
    private static final boolean APPLY_MEMORY_OPTIMIZATION = true;
    private GlobalRunTimeState initialState = null;
        
        
    private Set<GlobalRunTimeState> states = new HashSet<>();
    private Set<Transition> transitions = new HashSet<>();
    
    private Map<GlobalRunTimeState, List<Transition>> outTransitionsCache = new HashMap<>();
    private Map<GlobalRunTimeState, List<Transition>> inTransitionsCache = new HashMap<>();
    
    public void SetInitialState(GlobalRunTimeState state)
    {
        initialState = state;
        AddState(state);
    }
    
    public GlobalRunTimeState InitialState()
    {
        return initialState;
    }
    
    public void AddState(GlobalRunTimeState state)
    {
        if(APPLY_MEMORY_OPTIMIZATION)
        {
            if(states.contains(state))
                return;
        }
        states.add(state);
    }
    
    public boolean HasState(GlobalRunTimeState state)
    {
        return states.contains(state);
    }
    
    public Set<GlobalRunTimeState> States()
    {
        return states;
    }
    
    public void AddTransition(GlobalRunTimeState origin,Label label, GlobalRunTimeState destination)
    {
        Transition transtion = new Transition(StateFor(origin),label,StateFor(destination));
        
        if(APPLY_MEMORY_OPTIMIZATION)
        {
            if(transitions.contains(transtion))
                return;
        }
        //assert(HasState(origin));
        //AddState(origin);
        transitions.add(transtion);
        AddState(transtion.GetDestination());
        
        CacheTransitionOutputInput(transtion);
    }
    
    private GlobalRunTimeState StateFor(GlobalRunTimeState state)
    {
        return state;
        //return states.getOrDefault(state, state);
    }
    
    public Set<Transition> Transitions()
    {
        return transitions;
    }
    
    public boolean HasTransition(GlobalRunTimeState origin,Label label, GlobalRunTimeState destination)
    {
        return transitions.contains(new Transition(origin, label, destination));
    }  
    
    public List<Transition> GetOutTransitionsFor(GlobalRunTimeState state)
    {
        return outTransitionsCache.getOrDefault(state,Collections.EMPTY_LIST);
    }
    
    public List<Transition> GetInTransitionsFor(GlobalRunTimeState state)
    {
        return inTransitionsCache.getOrDefault(state,Collections.EMPTY_LIST);
    }
    
    public void RemoveTranstion(Transition t)
    {
        transitions.remove(t);
    }
    
    public void RemoveState(GlobalRunTimeState state)
    {
        for(Transition transition : GetOutTransitionsFor(state))
            RemoveTranstion(transition);
        
        for(Transition transition : GetInTransitionsFor(state))
            RemoveTranstion(transition);
        states.remove(state);
    }

    private void CacheTransitionOutputInput(Transition transtion)
    {
        GlobalRunTimeState origin = transtion.GetOrign();
        GlobalRunTimeState destination = transtion.GetDestination();
        
        List<Transition> cachedTrs;
        
        cachedTrs = outTransitionsCache.get(origin);
        if(cachedTrs == null)
        {
            cachedTrs = new LinkedList<>();
            outTransitionsCache.put(origin, cachedTrs);
        }
        cachedTrs.add(transtion);
                       
        
        cachedTrs = inTransitionsCache.get(destination);
        if(cachedTrs == null)
        {
            cachedTrs = new LinkedList<>();
            inTransitionsCache.put(destination, cachedTrs);
        }
        cachedTrs.add(transtion);
    }

    public int TransitionsSize()
    {
        return transitions.size();
    }
}
