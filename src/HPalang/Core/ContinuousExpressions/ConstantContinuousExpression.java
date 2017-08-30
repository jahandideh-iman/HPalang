/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Core.ContinuousExpressions;

import HPalang.Core.Expression;
import HPalang.Core.SimpleValuationContainer;
import HPalang.Core.ValuationContainer;

/**
 *
 * @author Iman Jahandideh
 */
public class ConstantContinuousExpression extends ContinuousExpressionT<ConstantContinuousExpression>
{
    private final float constant;
    
    public ConstantContinuousExpression(float constant)
    {
        this.constant = constant;
    }
    
    public float Value()
    {
        return constant;
    }
    
    @Override
    public String toString()
    {
        return String.valueOf(constant);
    }

    @Override
    protected boolean InternalEquals(ConstantContinuousExpression other)
    {
        return other.constant == this.constant;
    }

    @Override
    protected int InternalHashCode()
    {
        return Float.hashCode(constant);
    }

    @Override
    public boolean IsComputable(ValuationContainer valuations)
    {
        return false;
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
    
}
