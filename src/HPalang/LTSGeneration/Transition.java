/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration;

import HPalang.Core.Equalitable;
import HPalang.LTSGeneration.RunTimeStates.GlobalRunTimeState;

/**
 *
 * @author Iman Jahandideh
 */
public class Transition extends Equalitable<Transition>
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
