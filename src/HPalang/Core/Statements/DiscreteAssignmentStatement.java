/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Core.Statements;

import HPalang.Core.DiscreteExpression;
import HPalang.Core.DiscreteExpressions.DiscreteExpressionT;
import HPalang.Core.DiscreteVariable;

/**
 *
 * @author Iman Jahandideh
 */
public class DiscreteAssignmentStatement extends AbstractStatement<DiscreteAssignmentStatement>
{
    private final DiscreteVariable dVar;
    private final DiscreteExpression dExpr;

    public  DiscreteAssignmentStatement(DiscreteVariable dVar, DiscreteExpression dExpr)
    {
        this.dVar = dVar;
        this.dExpr = dExpr;
    }
    
    public DiscreteVariable Variable()
    {
        return dVar;
    }
    
    public DiscreteExpression Expression()
    {
        return dExpr;
    }
    
    @Override
    protected boolean InternalEquals(DiscreteAssignmentStatement other)
    {
        return dVar.equals(other.dVar)
                && dExpr.equals(other.dExpr);
    }

    @Override
    protected int InternalHashCode()
    {
        return 1;
    }
    
    @Override
    public String toString()
    {
        return "Assign " + dVar.toString();
    } 
}
