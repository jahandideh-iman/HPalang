/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Core.Expressions.Crawlers;

import HPalang.Core.DiscreteExpressions.TrueConst;
import HPalang.Core.Expressions.Visitors.TrueConstVisitor;

/**
 *
 * @author Iman Jahandideh
 */
public abstract class PostorderTrueConstCrawler implements TrueConstVisitor
{

    @Override
    public void Visit(TrueConst expr)
    {
        Process(expr);
    }
    
    protected abstract void Process(TrueConst expr);
}
