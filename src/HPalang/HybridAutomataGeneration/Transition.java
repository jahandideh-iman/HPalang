/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.HybridAutomataGeneration;

import HPalang.LTSGeneration.ConditionalLabel;


/**
 *
 * @author Iman Jahandideh
 */
public class Transition
{
        
    private Location origin;
    private Location destination;
    private ConditionalLabel label;

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
        if (other == null) {
            return false;
        }
        if (!Transition.class.isAssignableFrom(other.getClass())) {
            return false;
        }
        Transition otherTransition = (Transition) other;
        return otherTransition.destination.equals(destination) && otherTransition.origin.equals(origin) && otherTransition.label.equals(label);
    }
}
