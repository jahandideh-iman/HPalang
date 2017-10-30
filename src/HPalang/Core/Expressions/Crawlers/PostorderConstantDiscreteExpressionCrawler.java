/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Core.Expressions.Crawlers;

import HPalang.Core.DiscreteExpressions.ConstantDiscreteExpression;
import HPalang.Core.Expressions.Visitors.ConstantDiscreteExpressionVisitor;

/**
 *
 * @author Iman Jahandideh
 */
public abstract class PostorderConstantDiscreteExpressionCrawler implements ConstantDiscreteExpressionVisitor
{

    @Override
    public void Visit(ConstantDiscreteExpression expr)
    {
        Process(expr);
    }
    
    protected abstract void Process(ConstantDiscreteExpression expr);
    
}
