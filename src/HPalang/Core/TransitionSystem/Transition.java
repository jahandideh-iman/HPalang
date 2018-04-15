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
    private LTSState<T> origin;
    private LTSState<T> destination;
    private Label label;

    public Transition(LTSState<T> origin, Label label, LTSState<T> destination)
    {
        this.origin = origin;
        this.destination = destination;
        this.label = label;
    }

    public LTSState<T> GetOrign()
    {
        return origin;
    }

    public LTSState<T> GetDestination()
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
