/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Core.DiscreteExpressions;

import HPalang.Core.ExpressionVisitor;
import HPalang.Core.Expressions.Visitors.TrueConstVisitor;
import HPalang.Core.Visitor;

/**
 *
 * @author Iman Jahandideh
 */
public class TrueConst extends ConstantDiscreteExpression
{
    
    public TrueConst()
    {
        super(1);
    }
    
    @Override
    public void Visit(Visitor visitor)
    {
        if(visitor instanceof TrueConstVisitor)
            ((TrueConstVisitor) visitor).Visit(this);
    }
    
}
