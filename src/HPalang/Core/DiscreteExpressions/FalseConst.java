/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Core.DiscreteExpressions;

import HPalang.Core.ExpressionVisitor;
import HPalang.Core.Expressions.Visitors.FalseConstVisitor;
import HPalang.Core.Visitor;

/**
 *
 * @author Iman Jahandideh
 */
public class FalseConst extends ConstantDiscreteExpression
{
    
    public FalseConst()
    {
        super(0);
    }

    @Override
    public void Visit(Visitor visitor)
    {
        if(visitor instanceof FalseConstVisitor)
            ((FalseConstVisitor) visitor).Visit(this);
    }   
}
