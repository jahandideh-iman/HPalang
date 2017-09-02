/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.Labels;

import HPalang.Core.DiscreteExpressions.BinaryExpression;
import HPalang.Core.Equalitable;

/**
 *
 * @author Iman Jahandideh
 */
public class Guard extends Equalitable<Guard>
{
    private final BinaryExpression guardExpression;
    
    public Guard(BinaryExpression guardExpression)
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
