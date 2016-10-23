/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration;

import HPalang.LTSGeneration.RunTimeStates.GlobalRunTimeState;
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
    private class Transition
    {
        GlobalRunTimeState origin;
        GlobalRunTimeState destination;
        Label label;
        
        private Transition(GlobalRunTimeState origin,Label label, GlobalRunTimeState destination)
        {
            this.origin = origin;
            this.destination = destination;
            this.label = label;
        }
        
        @Override
        public boolean equals(Object other)
        {
            if(other == null)
                return false;
        
            if (!Transition.class.isAssignableFrom(other.getClass()))
                return false;
            
            Transition otherTransition = (Transition) other;
       
            return  otherTransition.destination.equals(destination)
                    && otherTransition.origin.equals(origin)
                    && otherTransition.label.equals(label);

        }
        
    }
    
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
        AddState(origin);
        AddState(destination);

        transitions.add(new Transition(origin,label,destination));
    }
    
    public boolean HasTransition(GlobalRunTimeState origin,Label label, GlobalRunTimeState destination)
    {
        return transitions.contains(new Transition(origin, label, destination));
    }  
}
