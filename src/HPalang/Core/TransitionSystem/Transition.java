/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Core.TransitionSystem;

import HPalang.Core.Equalitable;


/**
 *
 * @author Iman Jahandideh
 */
public class Transition<T> extends Equalitable<Transition>
{
    private StateWrapper<T> origin;
    private StateWrapper<T> destination;
    private Label label;

    public Transition(StateWrapper<T> origin, Label label, StateWrapper<T> destination)
    {
        this.origin = origin;
        this.destination = destination;
        this.label = label;
    }

    public StateWrapper<T> GetOrign()
    {
        return origin;
    }

    public StateWrapper<T> GetDestination()
    {
        return destination;
    }

    public Label GetLabel()
    {
        return label;
    }

    @Override
    protected boolean InternalEquals(Transition other)
    {
        return other.destination.equals(this.destination)
                && other.origin.equals(this.origin)
                && other.label.equals(this.label);
    }

    @Override
    protected int InternalHashCode()
    {
        return origin.hashCode() + destination.hashCode() + label.hashCode();
    }
    
}
