/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.HybridAutomataGeneration;

import HPalang.Core.TransitionSystem.LabeledTransitionSystem;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import HPalang.Core.TransitionSystem.StateWrapper;

/**
 *
 * @author Iman Jahandideh
 */
public class HybridAutomaton extends LabeledTransitionSystem<Location>
{
    private final List<String> variables = new LinkedList<>();
    
//    public void SetInitialState(Location location)
//    {
//        initialLocation = location;
//        if(locations.contains(location) == false)
//            locations.add(location);
//    }
    
    public Location GetInitialState()
    {
        return this.InitialState().InnerState();
    }
    
    public void AddLocation(Location location)
    {
        TryAddState(location);
    }
    
    public boolean HasLocation(Location location)
    {
        return this.HasState(location);
    }

    
    public void AddTransition(Location origin,HybridLabel label, Location destination)
    {
        StateWrapper<Location> orignLoc = this.TryAddState(origin);
        StateWrapper<Location> destLoc = this.TryAddState(destination);
        this.AddTransition(orignLoc, label, destLoc);
    }
    
    
    public boolean HasTransition(Location origin, HybridLabel label, Location destination)
    {
        StateWrapper<Location> orignLoc = this.TryAddState(origin);
        StateWrapper<Location> destLoc = this.TryAddState(destination);
        return this.HasTransition(orignLoc, label, destLoc);
    }  
    
//    public List<Transition> GetTransitionsFrom(Location state)
//    {
//        List<Transition> trans = new ArrayList<>();
//        for(Transition t : transitions)
//            if(t.GetOrign().equals(state))
//                trans.add(t);
//        
//        return trans;
//    }
//    
//    public List<Transition> GetTransitionsTo(Location state)
//    {
//        List<Transition> trans = new ArrayList<>();
//        for(Transition t : transitions)
//            if(t.GetDestination().equals(state))
//                trans.add(t);
//        
//        return trans;
//    }
//    
//    public void RemoveTranstion(Transition t)
//    {
//        transitions.remove(t);
//    }
//    
    
//    public void RemoveState(Location state)
//    {
//        for(Transition transition : GetTransitionsFrom(state))
//            RemoveTranstion(transition);
//        
//        for(Transition transition : GetTransitionsTo(state))
//            RemoveTranstion(transition);
//        locations.remove(state);
//
//    }

    void AddVariable(String name)
    {
        variables.add(name);
    }

    public Collection<String> Variables()
    {
        return variables;
    }

    public Collection<Location> GetLocations()
    {
        return this.States();
    }
}
