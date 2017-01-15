/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Core.Statements;

import HPalang.Core.DiscreteExpression;
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
    
    @Override
    protected boolean InternalEquals(DiscreteAssignmentStatement other)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected int InternalHashCode()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
