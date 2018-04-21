/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Mocks;

import HPalang.Core.Expression;
import HPalang.Core.ExpressionVisitor;
import HPalang.Core.Expressions.Visitors.GeneralExpressionVisitor;
import HPalang.Core.ValuationContainer;
import HPalang.Core.Visitor;

/**
 *
 * @author Iman Jahandideh
 */
public class EmptyExpression implements Expression
{
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
        return this;
    }

    @Override
    public void Visit(Visitor visitor)
    {
        if(visitor instanceof GeneralExpressionVisitor)
            ((GeneralExpressionVisitor)visitor).Visit(this);
    }
    
}
