/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.Labels;

import HPalang.Core.Equalitable;
import HPalang.Core.Expression;
import HPalang.Core.ExpressionVisitor;
import HPalang.Core.ValuationContainer;
import HPalang.Core.Visitor;

/**
 *
 * @author Iman Jahandideh
 */
public class Guard extends Equalitable<Guard> implements Expression 
{
    private final Expression guardExpression;
    
    public Guard(Expression guardExpression)
    {
        // TODO: Assert that the guard expression is a comparison expression.
        this.guardExpression = guardExpression;
    }

    @Override
    protected boolean InternalEquals(Guard other)
    {
        return this.guardExpression.equals(other.guardExpression);
    }

    @Override
    protected int InternalHashCode()
    {
        return 0;
    }

    public Expression Expression()
    {
        return guardExpression;
    }

    @Override
    public String toString()
    {
        return guardExpression.toString();
    }

    @Override
    public boolean IsComputable(ValuationContainer valuations)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int Evaluate(ValuationContainer valuations)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Expression PartiallyEvaluate(ValuationContainer valuations)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void Visit(Visitor visitor)
    {
        if(visitor instanceof GuardVisitor)
            ((GuardVisitor) visitor).Visit(this);
    }
    
    
    
    
}
