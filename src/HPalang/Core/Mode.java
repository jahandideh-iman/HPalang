/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Core;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;
import java.util.Set;

/**
 *
 * @author Iman Jahandideh
 */
public class Mode extends Equalitable<Mode>
{
    private final String invariant;
    private final Set<DifferentialEquation> equations;
    private final String guard;
    private Queue<Statement> actions = new LinkedList<>();
    
    public Mode(String inv, Set<DifferentialEquation> odes, String guard, Queue<Statement> actions )
    {
        this.invariant = inv;
        this.equations = odes;
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
    
    public Set<DifferentialEquation> GetEquations()
    {
        return equations;
    }

    public String GetInvarient()
    {
        return invariant;
    }
    
    @Override
    protected boolean InternalEquals(Mode other)
    {
        return other.invariant.equals(this.invariant)
                && other.equations.equals(this.equations)
                && other.guard.equals(this.guard)
                && other.actions.equals(this.actions);
    }

    @Override
    protected int InternalHashCode()
    {
        int hash = 7;
        hash = 19 * hash + Objects.hashCode(this.invariant);
        hash = 19 * hash + Objects.hashCode(this.equations);
        hash = 19 * hash + Objects.hashCode(this.guard);
        return hash;
    }
    
    public static Mode None()
    {
        return new Mode("", EquationsFrom(DifferentialEquation.Empty()), "", Statement.EmptyStatements());
    }
    
    static public Set<DifferentialEquation> EquationsFrom(DifferentialEquation ...equations)
    {
        Set<DifferentialEquation> odes = new HashSet<>();
        for(DifferentialEquation e : equations)
            odes.add(e);
        return odes;
    }
}
