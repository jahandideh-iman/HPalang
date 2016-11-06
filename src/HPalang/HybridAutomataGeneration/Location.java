/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.HybridAutomataGeneration;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 *
 * @author Iman Jahandideh
 */
public class Location
{
    private final Set<String> invariants = new HashSet<>();
    private final Set<String> equations = new HashSet<>();
    
    public void AddInvariant(String invarient)
    {
        invariants.add(invarient);
    }
    
    public void AddEquation(String equation)
    {
        equations.add(equation);
    }
    
    @Override
    public boolean equals(Object other)
    {
         if(other == null)
            return false;
        
        if (!this.getClass().isAssignableFrom(other.getClass()))
            return false;
            
        Location otherLocation = (Location) other;
        return this.invariants.equals(otherLocation.invariants)
                && this.equations.equals(otherLocation.equations);
    }

    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.invariants);
        hash = 97 * hash + Objects.hashCode(this.equations);
        return hash;
    }

    public Set<String> GetInvarients()
    {
        return Collections.unmodifiableSet(invariants);
    }

    public Set<String> GetEquations()
    {
        return Collections.unmodifiableSet(equations);
    }
}
