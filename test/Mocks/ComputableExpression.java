/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Mocks;

import HPalang.Core.Expression;
import HPalang.Core.ExpressionVisitor;
import HPalang.Core.ValuationContainers.SimpleValuationContainer;
import HPalang.Core.ValuationContainer;
import HPalang.Core.Visitor;

/**
 *
 * @author Iman Jahandideh
 */
public class ComputableExpression implements Expression
{
    private final int value;
    public ComputableExpression(int value)
    {
        this.value = value;
    }
    
    @Override
    public boolean IsComputable(ValuationContainer valuations)
    {
        return true;
    }

    @Override
    public int Evaluate(ValuationContainer valuations)
    {
        return value;
    }

    @Override
    public Expression PartiallyEvaluate(ValuationContainer valuations)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void Visit(Visitor visitor)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
