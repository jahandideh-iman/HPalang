/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Core.DiscreteExpressions;

import HPalang.Core.Expression;
import HPalang.Core.SimpleValuationContainer;
import HPalang.Core.ValuationContainer;

/**
 *
 * @author Iman Jahandideh
 */
public class ConstantDiscreteExpression extends DiscreteExpressionT<ConstantDiscreteExpression>
{
    private final int constant;
    
    public ConstantDiscreteExpression(int constant)
    {
        this.constant = constant;
    }
    
    public int Constant()
    {
        return constant;
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

    @Override
    public boolean IsComputable(ValuationContainer valuations)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Expression PartiallyEvaluate(ValuationContainer valuations)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
