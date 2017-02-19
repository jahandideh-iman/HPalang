/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Core.Statements;

import HPalang.Core.ContinuousExpression;
import HPalang.Core.ContinuousVariable;

/**
 *
 * @author Iman Jahandideh
 */
public class ContinuousAssignmentStatement extends AbstractStatement<ContinuousAssignmentStatement>
{
    private final ContinuousVariable var;
    private final ContinuousExpression expr;

    public  ContinuousAssignmentStatement(ContinuousVariable var, ContinuousExpression expr)
    {
        this.var = var;
        this.expr = expr;
    }
    
    public ContinuousVariable Variable()
    {
        return var;
    }
    
    public ContinuousExpression Expression()
    {
        return expr;
    }
    
    @Override
    protected boolean InternalEquals(ContinuousAssignmentStatement other)
    {
        return var.equals(other.var)
                && expr.equals(other.expr);
    }

    @Override
    protected int InternalHashCode()
    {
        return 1;
    }
    
    @Override
    public String toString()
    {
        return "Assign " + var.toString();
    } 
}
