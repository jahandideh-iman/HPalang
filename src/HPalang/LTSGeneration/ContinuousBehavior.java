/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration;

import HPalang.Statements.Statement;
import java.util.List;
import java.util.Queue;

/**
 *
 * @author Iman Jahandideh
 */
public class ContinuousBehavior
{
    private String invariant;
    private String equation;
    private String guard;
    private Queue<Statement> actions;
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
}
