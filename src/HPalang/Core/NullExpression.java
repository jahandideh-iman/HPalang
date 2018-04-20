/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Core;

import HPalang.Core.Expressions.Visitors.GeneralExpressionVisitor;

/**
 *
 * @author Iman Jahandideh
 */
public class NullExpression extends Equalitable<NullExpression> implements Expression
{
    private final String expression;
    public NullExpression()
    {
        this.expression = "null";
    }
    
    public NullExpression(String expression)
    {
        this.expression = expression;
    }
    
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
        return this;
    }

    @Override
    protected boolean InternalEquals(NullExpression other)
    {
        return this.expression.equals(other.expression);
    }

    @Override
    protected int InternalHashCode()
    {
        return 0;
    }

    @Override
    public void Visit(Visitor visitor)
    {
        if(visitor instanceof GeneralExpressionVisitor)
            ((GeneralExpressionVisitor)visitor).Visit(this);
    }
    
}
