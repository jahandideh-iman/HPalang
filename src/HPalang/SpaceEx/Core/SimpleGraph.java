/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.SpaceEx.Core;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Iman Jahandideh
 * @param <NodeT>
 * @param <LabelT>
 * @param <TransitionT>
 */
public class SimpleGraph<NodeT extends Node, LabelT extends Label, TransitionT extends Transition<NodeT, LabelT>>
{   
    private final Set<NodeT> nodes = new HashSet<>();  
    private final List<TransitionT> transitions = new LinkedList<>();
    private final List<GraphListener<NodeT>> listeners = new LinkedList<>();
    
    public void AddListener(GraphListener<NodeT> listener)
    {
        listeners.add(listener);
    }
    
    public void AddNode(NodeT node)
    {
        if(HasNode(node) == false)
            listeners.forEach((l)-> l.OnNodeAdded(node));
        nodes.add(node);
        

    }
    
    public boolean HasNode(NodeT node)
    {
        return nodes.contains(node);
    }
    
    public Collection<NodeT> GetNodes()
    {
        return nodes;
    }
    
    public void AddTransition(TransitionT transition)
    {
        if(transitions.contains(transition))
            return;
        AddNode(transition.GetOrign());
        transitions.add(transition);
        AddNode(transition.GetDestination());
    }
    
    public Collection<TransitionT> GetTransitions()
    {
        return transitions;
    }
    
    public boolean HasTransition(NodeT origin,LabelT label, NodeT destination)
    {
        for(TransitionT tran : transitions)
            if(tran.GetOrign().equals(origin) 
                    && tran.GetLabel().equals(label)
                    && tran.GetDestination().equals(destination))
                return true;
        return false;
    }  
    
    public Collection<TransitionT> GetTransitionsFrom(NodeT origin)
    {
        List<TransitionT> trans = new LinkedList<>();
        
        for(TransitionT t : transitions)
            if(t.GetOrign().equals(origin))
                trans.add(t);
        
        return trans;
    }
    
    public Collection<TransitionT>GetTransitionsTo(NodeT destination)
    {
        List<TransitionT> trans = new LinkedList<>();
        
        for(TransitionT t : transitions)
            if(t.GetDestination().equals(destination))
                trans.add(t);
        
        return trans;
    }
    
    public void RemoveTranstion(TransitionT t)
    {
        transitions.remove(t);
    }
    
    
    public void RemoveNode(NodeT node)
    {
        for(TransitionT transition : GetTransitionsFrom(node))
            RemoveTranstion(transition);
        
        for(TransitionT transition: GetTransitionsTo(node))
            RemoveTranstion(transition);
        
        nodes.remove(node);
    }
}


