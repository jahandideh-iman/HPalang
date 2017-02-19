/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Core.DiscreteExpressions;

import HPalang.Core.DiscreteExpression;
import HPalang.Core.ValuationContainer;

/**
 *
 * @author Iman Jahandideh
 */
public class ConstantDiscreteExpression extends DiscreteExpression<ConstantDiscreteExpression>
{
    private final int constant;
    
    public ConstantDiscreteExpression(int constant)
    {
        this.constant = constant;
    }
    
    @Override
    public int Evaluate(ValuationContainer valuations)
    {
        return constant;
    }

    @Override
    protected boolean InternalEquals(ConstantDiscreteExpression other)
    {
        return this.constant == other.constant;
    }

    @Override
    protected int InternalHashCode()
    {
        return Integer.hashCode(constant);
    }
    
}
