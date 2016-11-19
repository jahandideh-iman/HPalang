/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.RunTimeStates;

import HPalang.Core.Statement;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Queue;

/**
 *
 * @author Iman Jahandideh
 */
public class ContinuousBehavior
{
    private final String invariant;
    private final String equation;
    private final String guard;
    private Queue<Statement> actions = new LinkedList<>();
    
    public ContinuousBehavior(String inv, String ode, String guard, Queue<Statement> actions )
    {
        this.invariant = inv;
        this.equation = ode;
        this.guard = guard;
        this.actions = actions;
    }
    
    public Queue<Statement> GetActions()
    {
        return actions;
    }
    
    public String GetGuard()
    {
        return guard;
    }
    
    public String GetEquation()
    {
        return equation;
    }

    public String GetInvarient()
    {
        return invariant;
    }
    
    @Override
    public boolean equals(Object other)
    {
        if(other == null)
            return false;
        
        if (!this.getClass().isAssignableFrom(other.getClass()))
            return false;
            
        ContinuousBehavior otherBehavior = (ContinuousBehavior) other;
        
        return otherBehavior.invariant.equals(this.invariant)
                && otherBehavior.equation.equals(this.equation)
                && otherBehavior.guard.equals(this.guard)
                && Arrays.equals(otherBehavior.actions.toArray(), this.actions.toArray());
    }

    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 19 * hash + Objects.hashCode(this.invariant);
        hash = 19 * hash + Objects.hashCode(this.equation);
        hash = 19 * hash + Objects.hashCode(this.guard);
        return hash;
    }
    
    @Override
    public String toString()
    {
        String actionsStr = "{";
        
//        for(Statement s : actions)
//            actionsStr+= s.toString() + ",";
//        actionsStr += "}";
//        return "Behavior("+invariant +"," + equation + "," + guard + "," +actionsStr+ ")";

        return "B(" + invariant + ")";
    }

}
