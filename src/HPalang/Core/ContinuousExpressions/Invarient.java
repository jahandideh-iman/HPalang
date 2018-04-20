/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Core.ContinuousExpressions;

import HPalang.Core.Equalitable;
import HPalang.Core.Expression;
import HPalang.Core.Expressions.Visitors.InvarientVisitor;
import HPalang.Core.ValuationContainer;
import HPalang.Core.Visitor;


/**
 *
 * @author Iman Jahandideh
 */
public class Invarient extends Equalitable<Invarient> implements Expression 
{
    private final Expression invariantExpression;
    
    // TODO: Check expression is Comparision experssion or True/False Expression
    public Invarient(Expression expression)
    {
        this.invariantExpression = expression;
    }
    
    @Override
    public String toString()
    {
        return invariantExpression.toString();
    }

    @Override
    protected boolean InternalEquals(Invarient other)
    {
        return this.invariantExpression.equals(other.invariantExpression);
    }

    @Override
    protected int InternalHashCode()
    {
        return invariantExpression.hashCode();
    }

    public Expression Expression()
    {
        return invariantExpression;
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
        if(visitor instanceof InvarientVisitor)
            ((InvarientVisitor) visitor).Visit(this);
    }
}
