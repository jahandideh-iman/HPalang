/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.HybridAutomataGeneration;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Iman Jahandideh
 */
public class Location
{
    private List<String> invariants = new LinkedList<>();
    private List<String> equations = new LinkedList<>();
    
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
        
        return Arrays.equals(this.equations.toArray(), otherLocation.equations.toArray())
                && Arrays.equals(this.invariants.toArray(), otherLocation.invariants.toArray()) ;
    }

    public List<String> GetInvarients()
    {
        return invariants;
    }

    public List<String> GetEquations()
    {
        return equations;
    }
}
