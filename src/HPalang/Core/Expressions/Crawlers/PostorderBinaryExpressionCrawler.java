/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Core.Expressions.Crawlers;

import HPalang.Core.DiscreteExpressions.BinaryExpression;
import HPalang.Core.Expressions.Visitors.BinaryExpressionVisitor;

/**
 *
 * @author Iman Jahandideh
 */
public abstract class PostorderBinaryExpressionCrawler implements BinaryExpressionVisitor
{

    @Override
    public void Visit(BinaryExpression expr)
    {
        expr.Operand1().Visit(this);
        expr.Operand2().Visit(this);
        Process(expr);
    }
    
    protected abstract void Process(BinaryExpression expr);
}
