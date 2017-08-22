/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Mocks;

import HPalang.Core.Equalitable;
import HPalang.Core.Expression;
import HPalang.Core.ValuationContainer;

/**
 *
 * @author Iman Jahandideh
 */
public class NullExpression extends Equalitable<NullExpression> implements Expression
{

    @Override
    public boolean IsComputable(ValuationContainer valuations)
    {
        return true;
    }

    @Override
    public int Evaluate(ValuationContainer valuations)
    {
        return 0;
    }

    @Override
    public Expression PartiallyEvaluate(ValuationContainer valuations)
    {
        return null;
    }

    @Override
    protected boolean InternalEquals(NullExpression other)
    {
        return true;
    }

    @Override
    protected int InternalHashCode()
    {
        return 0;
    }
    
}
