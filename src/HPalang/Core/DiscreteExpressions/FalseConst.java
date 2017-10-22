/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Core.DiscreteExpressions;

import HPalang.Core.ExpressionVisitor;

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
    public void Visit(ExpressionVisitor visitor)
    {
        visitor.Visit(this);
    }
    
    
    
}
