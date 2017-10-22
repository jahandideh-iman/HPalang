/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.Labels;

import HPalang.Core.Equalitable;
import HPalang.Core.Expression;

/**
 *
 * @author Iman Jahandideh
 */
public class Guard extends Equalitable<Guard>
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
    
    
    
    
}
