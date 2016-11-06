/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.HybridAutomataGeneration;

import HPalang.LTSGeneration.ConditionalLabel;
import java.util.Objects;


/**
 *
 * @author Iman Jahandideh
 */
public class Transition
{
        
    private final Location origin;
    private final Location destination;
    private final ConditionalLabel label;

    public Transition(Location origin, ConditionalLabel label, Location destination)
    {
        this.origin = origin;
        this.destination = destination;
        this.label = label;
    }

    public Location GetOrign()
    {
        return origin;
    }

    public Location GetDestination()
    {
        return destination;
    }

    public ConditionalLabel GetLabel()
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
        hash = 97 * hash + Objects.hashCode(this.origin);
        hash = 97 * hash + Objects.hashCode(this.destination);
        hash = 97 * hash + Objects.hashCode(this.label);
        return hash;
    }
}
