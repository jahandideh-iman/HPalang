/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration;

import HPalang.LTSGeneration.RunTimeStates.GlobalRunTimeState;

/**
 *
 * @author Iman Jahandideh
 */
public class Transition
{
    
    private GlobalRunTimeState origin;
    private GlobalRunTimeState destination;
    private Label label;

    public Transition(GlobalRunTimeState origin, Label label, GlobalRunTimeState destination)
    {
        this.origin = origin;
        this.destination = destination;
        this.label = label;
    }

    public GlobalRunTimeState GetOrign()
    {
        return origin;
    }

    public GlobalRunTimeState GetDestination()
    {
        return destination;
    }

    public Label GetLabel()
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
