/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.SpaceEx.Core;

/**
 *
 * @author Iman Jahandideh
 */
public class Transition<NodeT extends Node, LabelT extends Label>
{
    private final NodeT origin;
    private final NodeT destination;
    private final LabelT label;

    public Transition(NodeT origin, LabelT label, NodeT destination)
    {
        this.origin = origin;
        this.destination = destination;
        this.label = label;
    }

    public NodeT GetOrign()
    {
        return origin;
    }

    public NodeT GetDestination()
    {
        return destination;
    }

    public LabelT GetLabel()
    {
        return label;
    }

    @Override
    public boolean equals(Object other)
    {
        if (other == null) 
            return false;
        
        if (!Transition.class.isAssignableFrom(other.getClass())) 
            return false;
        
        Transition otherTransition = (Transition) other;
        return otherTransition.destination.equals(this.destination) 
                && otherTransition.origin.equals(this.origin) 
                && otherTransition.label.equals(this.label);
    }

    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 97 * hash + this.origin.hashCode();
        hash = 97 * hash + this.label.hashCode();
        hash = 97 * hash + this.destination.hashCode();
        return hash;
    }
}
