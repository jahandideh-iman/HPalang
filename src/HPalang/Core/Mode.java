/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Core;

import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

/**
 *
 * @author Iman Jahandideh
 */
public class Mode extends Equalitable<Mode>
{
    private final String invariant;
    private final DifferentialEquation equation;
    private final String guard;
    private Queue<Statement> actions = new LinkedList<>();
    
    public Mode(String inv, DifferentialEquation ode, String guard, Queue<Statement> actions )
    {
        this.invariant = inv;
        this.equation = ode;
        this.guard = guard;
        this.actions = actions;
    }
    
    public Queue<Statement> Actions()
    {
        return actions;
    }
    
    public String Guard()
    {
        return guard;
    }
    
    public DifferentialEquation GetEquation()
    {
        return equation;
    }

    public String GetInvarient()
    {
        return invariant;
    }
    
    @Override
    protected boolean InternalEquals(Mode other)
    {
        return other.invariant.equals(this.invariant)
                && other.equation.equals(this.equation)
                && other.guard.equals(this.guard)
                && other.actions.equals(this.actions);
    }

    @Override
    protected int InternalHashCode()
    {
        int hash = 7;
        hash = 19 * hash + Objects.hashCode(this.invariant);
        hash = 19 * hash + Objects.hashCode(this.equation);
        hash = 19 * hash + Objects.hashCode(this.guard);
        return hash;
    }
    
    public static Mode None()
    {
        return new Mode("", DifferentialEquation.Empty(), "", Statement.EmptyStatements());
    }
}
