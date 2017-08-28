/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration;

import HPalang.LTSGeneration.RunTimeStates.GlobalRunTimeState;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Iman Jahandideh
 */
public class LabeledTransitionSystem
{    
    private Set<GlobalRunTimeState> states = new HashSet<>();
    private GlobalRunTimeState initialState = null;
    
    private List<Transition> transitions = new LinkedList<>();
    
    public void SetInitialState(GlobalRunTimeState state)
    {
        initialState = state;
        if(states.contains(state) == false)
            states.add(state);
    }
    
    public GlobalRunTimeState GetInitialState()
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
    
    public Set<GlobalRunTimeState> GetStates()
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
    }
    
    public List<Transition> GetTransitions()
    {
        return transitions;
    }
    
    public boolean HasTransition(GlobalRunTimeState origin,Label label, GlobalRunTimeState destination)
    {
        return transitions.contains(new Transition(origin, label, destination));
    }  
    
    public List<Transition> GetOutTransitionsFor(GlobalRunTimeState state)
    {
        List<Transition> trans = new ArrayList<>();
        for(Transition t : transitions)
            if(t.GetOrign().equals(state))
                trans.add(t);
        
        return trans;
    }
    
    public List<Transition> GetInTransitionFor(GlobalRunTimeState state)
    {
        List<Transition> trans = new ArrayList<>();
        for(Transition t : transitions)
            if(t.GetDestination().equals(state))
                trans.add(t);
        
        return trans;
    }
    
    public void RemoveTranstion(Transition t)
    {
        transitions.remove(t);
    }
    
    
    public void RemoveState(GlobalRunTimeState state)
    {
        for(Transition transition : GetOutTransitionsFor(state))
            RemoveTranstion(transition);
        
        for(Transition transition : GetInTransitionFor(state))
            RemoveTranstion(transition);
        states.remove(state);
    }
    
}
