/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Core;

import HPalang.Core.ContinuousExpressions.DifferentialEquation;
import HPalang.Core.ContinuousExpressions.Invarient;
import HPalang.Core.DiscreteExpressions.FalseConst;
import HPalang.LTSGeneration.Labels.Guard;
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
    private final String name;
    private Invarient invariant;
    private Set<DifferentialEquation> equations = new HashSet<>();
    private Guard guard;
    private Queue<Statement> actions = new LinkedList<>();
    
    public Mode(String name)
    {
        this.name = name;
    }
    public Mode(String name, Invarient inv, Set<DifferentialEquation> odes, Guard guard, Queue<Statement> actions )
    {
        this.name = name;
        this.invariant = inv;
        this.equations = odes;
        this.guard = guard;
        this.actions = actions;
    }
    
    public void SetInvarient(Invarient invariant)
    {
        this.invariant = invariant;
    }
    
    public void SetGuard(Guard guard)
    {
        this.guard = guard;
    }
    
    public void AddDifferentialEquation(DifferentialEquation ode)
    {
        equations.add(ode);
    }
    
    public void AddAction(Statement statment)
    {
        actions.add(statment);
    }
    
    public Queue<Statement> Actions()
    {
        return actions;
    }
    
    public Guard Guard()
    {
        return guard;
    }
    
    public Set<DifferentialEquation> GetEquations()
    {
        return equations;
    }

    public Invarient GetInvarient()
    {
        return invariant;
    }
    
    @Override
    protected boolean InternalEquals(Mode other)
    {
        return other.name.equals(this.name)
                && other.invariant.equals(this.invariant)
                && other.equations.equals(this.equations)
                && other.guard.equals(this.guard)
                && other.actions.equals(this.actions);
    }

    @Override
    protected int InternalHashCode()
    {
        return 0;
    }
    
    public static Mode None()
    {
        return new Mode("None",new Invarient(new FalseConst()), EquationsFrom(DifferentialEquation.Empty()), CreationUtilities.CreateTrivialFlaseGuard(), Statement.EmptyStatements());
    }
    
    static public Set<DifferentialEquation> EquationsFrom(DifferentialEquation ...equations)
    {
        Set<DifferentialEquation> odes = new HashSet<>();
        for(DifferentialEquation e : equations)
            odes.add(e);
        return odes;
    }

    public String Name()
    {
        return name;
    }
}
