/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Core.Statements;

import HPalang.Core.Expression;
import HPalang.Core.Variable;

/**
 *
 * @author Iman Jahandideh
 */
public class AssignmentStatement extends AbstractStatement<AssignmentStatement>
{
    private final Variable var;
    private final Expression expr;

    public  AssignmentStatement(Variable var, Expression expr)
    {
        this.var = var;
        this.expr = expr;
    }
    
     public Variable Variable()
    {
        return var;
    }
    
    public Expression Expression()
    {
        return expr;
    }
    
    @Override
    protected boolean InternalEquals(AssignmentStatement other)
    {
        return var.equals(other.var)
                && expr.equals(other.expr);
    }

    @Override
    protected int InternalHashCode()
    {
        return var.hashCode() + expr.hashCode();
    }
}
