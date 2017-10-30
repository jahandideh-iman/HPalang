/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Core.Expressions.Crawlers;

import HPalang.Core.ContinuousExpressions.ConstantContinuousExpression;
import HPalang.Core.Expressions.Visitors.ConstantContinuousExpressionVisitor;

/**
 *
 * @author Iman Jahandideh
 */
public abstract class PostorderConstantContinousExpressionCrawler implements ConstantContinuousExpressionVisitor
{

    @Override
    public void Visit(ConstantContinuousExpression expr)
    {
        Process(expr);
    }
    
    protected abstract void Process(ConstantContinuousExpression expr);
}
