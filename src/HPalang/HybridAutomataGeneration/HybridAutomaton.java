/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.HybridAutomataGeneration;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Iman Jahandideh
 */
public class HybridAutomaton
{
    private final Set<Location> locations = new HashSet<>();
    private Location initialLocation = null;
    
    private final List<Transition> transitions = new LinkedList<>();
    private final List<String> variables = new LinkedList<>();
    
    public void SetInitialState(Location location)
    {
        initialLocation = location;
        if(locations.contains(location) == false)
            locations.add(location);
    }
    
    public Location GetInitialState()
    {
        return initialLocation;
    }
    
    public void AddLocation(Location location)
    {
        locations.add(location);
    }
    
    public boolean HasLocation(Location location)
    {
        return locations.contains(location);
    }
    
    public Set<Location> GetLocations()
    {
        return locations;
    }
    
    public void AddTransition(Location origin,HybridLabel label, Location destination)
    {
        Transition transtion = new Transition(origin,label,destination);
        if(transitions.contains(transtion))
            return;
        AddLocation(origin);
        transitions.add(transtion);
        AddLocation(destination);
    }
    
    public List<Transition> GetTransitions()
    {
        return transitions;
    }
    
    public boolean HasTransition(Location origin, HybridLabel label, Location destination)
    {
        return transitions.contains(new Transition(origin, label, destination));
    }  
    
    public List<Transition> GetTransitionsFrom(Location state)
    {
        List<Transition> trans = new ArrayList<>();
        for(Transition t : transitions)
            if(t.GetOrign().equals(state))
                trans.add(t);
        
        return trans;
    }
    
    public List<Transition> GetTransitionsTo(Location state)
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
    
    
    public void RemoveState(Location state)
    {
        for(Transition transition : GetTransitionsFrom(state))
            RemoveTranstion(transition);
        
        for(Transition transition : GetTransitionsTo(state))
            RemoveTranstion(transition);
        locations.remove(state);

    }

    void AddVariable(String name)
    {
        variables.add(name);
    }

    public Iterable<String> Variables()
    {
        return variables;
    }
}
