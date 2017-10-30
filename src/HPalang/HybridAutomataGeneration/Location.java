/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.HybridAutomataGeneration;

import HPalang.Core.ContinuousExpressions.Invarient;
import HPalang.Core.ContinuousExpressions.DifferentialEquation;
import HPalang.Core.Equalitable;
import HPalang.LTSGeneration.RunTimeStates.GlobalRunTimeState;
import HPalang.LTSGeneration.RunTimeStates.PhysicalActorState;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Iman Jahandideh
 */
public class Location extends Equalitable<Location>
{
    private final Set<Invarient> invariants = new HashSet<>();
    private final Set<DifferentialEquation> equations = new HashSet<>();
    private final String name;
    
    public Location(String name)
    {
        this.name = name;
    }
 
    public void AddInvariant(Invarient invarient)
    {
        invariants.add(invarient);
    }
    
    public void AddEquation(DifferentialEquation equation)
    {
        equations.add(equation);
    }
    
    public void AddEquations(Set<DifferentialEquation> newEquations)
    {
        this.equations.addAll(newEquations);
    }
    

    public Set<Invarient> GetInvarients()
    {
        return Collections.unmodifiableSet(invariants);
    }

    public Set<DifferentialEquation> GetEquations()
    {
        return Collections.unmodifiableSet(equations);
    }

    @Override
    protected boolean InternalEquals(Location other)
    {
        return other.name.equals(this.name) &&
                other.equations.equals(this.equations) &&
                other.invariants.equals(this.invariants);
    }

    @Override
    protected int InternalHashCode()
    {
        return 0;
    }

    public String Name()
    {
        return name;
    }

}
