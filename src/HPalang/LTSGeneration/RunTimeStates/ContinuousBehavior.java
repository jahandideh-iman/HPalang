/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.RunTimeStates;

import HPalang.Core.Equalitable;
import HPalang.Core.Statement;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

/**
 *
 * @author Iman Jahandideh
 */
public class ContinuousBehavior extends Equalitable<ContinuousBehavior>
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
    public String toString()
    {
        String actionsStr = "{";
        
//        for(Statement s : actions)
//            actionsStr+= s.toString() + ",";
//        actionsStr += "}";
//        return "Behavior("+invariant +"," + equation + "," + guard + "," +actionsStr+ ")";

        return "B(" + invariant + ")";
    }

    @Override
    protected boolean InternalEquals(ContinuousBehavior other)
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
}
