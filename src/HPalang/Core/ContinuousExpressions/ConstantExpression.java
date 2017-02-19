/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Core.ContinuousExpressions;

import HPalang.Core.ContinuousExpression;
import HPalang.Core.ValuationContainer;

/**
 *
 * @author Iman Jahandideh
 */
public class ConstantExpression extends ContinuousExpression<ConstantExpression>
{
    private final float constant;
    
    public ConstantExpression(float constant)
    {
        this.constant = constant;
    }
    
    @Override
    public String toString()
    {
        return String.valueOf(constant);
    }

    @Override
    protected boolean InternalEquals(ConstantExpression other)
    {
        return other.constant == this.constant;
    }

    @Override
    protected int InternalHashCode()
    {
        return Float.hashCode(constant);
    }
    
}
