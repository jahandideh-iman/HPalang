/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Parser.SubParsers.Expression;

import HPalang.Core.Expression;

/**
 *
 * @author Iman Jahandideh
 */
public class ExpressionHolder
{
    private Expression expression = null;
    
    // TODO: Rename this?
    public void TrySetExpression(Expression expr)
    {
        assert expression == null;
        this.expression = expr;
    }
    
    public Expression Expression()
    {
        return expression;
    }
}
