/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Core.Expressions.Crawlers;

import HPalang.Core.DiscreteExpressions.FalseConst;
import HPalang.Core.Expressions.Visitors.FalseConstVisitor;

/**
 *
 * @author Iman Jahandideh
 */
public abstract class PostorderFalseConstCrawler implements FalseConstVisitor
{
    @Override
    public void Visit(FalseConst expr)
    {
        Process(expr);
    }
    
    protected abstract void Process(FalseConst expr);
}
