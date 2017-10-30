/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Core.Expressions.Crawlers;

import HPalang.Core.DiscreteExpressions.VariableExpression;
import HPalang.Core.Expressions.Visitors.VariableExpressionVisitor;

/**
 *
 * @author Iman Jahandideh
 */
public abstract class PostorderVariableExpressionCrawler implements VariableExpressionVisitor
{

    @Override
    public void Visit(VariableExpression expr)
    {
        Process(expr);
    }
    
    protected abstract void Process(VariableExpression expr);
    
}
