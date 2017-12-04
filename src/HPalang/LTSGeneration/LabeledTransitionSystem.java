/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration;

import HPalang.LTSGeneration.RunTimeStates.GlobalRunTimeState;
import java.util.ArrayList;
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
    private Set<GlobalRunTimeState> states = new HashSet<>();
    private GlobalRunTimeState initialState = null;
    
    private Set<Transition> transitions = new HashSet<>();
    
    private Map<GlobalRunTimeState, List<Transition>> outTransitionsCache = new HashMap<>();
    private Map<GlobalRunTimeState, List<Transition>> inTransitionsCache = new HashMap<>();
    
    public void SetInitialState(GlobalRunTimeState state)
    {
        initialState = state;
        if(states.contains(state) == false)
            states.add(state);
    }
    
    public GlobalRunTimeState InitialState()
    {
        return initialState;
    }
    
    public void AddState(GlobalRunTimeState state)
    {
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
        Transition transtion = new Transition(origin,label,destination);
        if(transitions.contains(transtion))
            return;
        assert(HasState(origin));
        //AddState(origin);
        transitions.add(transtion);
        AddState(destination);
        
        CacheTransitionOutputInput(transtion);
    }
    
    public List<Transition> Transitions()
    {
        return new ArrayList<>(transitions);
    }
    
    public boolean HasTransition(GlobalRunTimeState origin,Label label, GlobalRunTimeState destination)
    {
        return transitions.contains(new Transition(origin, label, destination));
    }  
    
    public List<Transition> GetOutTransitionsFor(GlobalRunTimeState state)
    {
        List<Transition> trans = outTransitionsCache.get(state);
        if(trans == null)
            trans = new LinkedList<>();
        return trans;
        
//        List<Transition> trans = new LinkedList<>();
//
//        for(Transition t : transitions)
//            if(t.GetOrign().equals(state))
//                trans.add(t);
//        
//        return trans;
    }
    
    public List<Transition> GetInTransitionsFor(GlobalRunTimeState state)
    {
        List<Transition> trans = inTransitionsCache.get(state);
        if(trans == null)
            trans = new LinkedList<>();
        return trans;
        
//        List<Transition> trans = new LinkedList<>();
//        for(Transition t : transitions)
//            if(t.GetDestination().equals(state))
//                trans.add(t);
//        
//        return trans;
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
    
}
