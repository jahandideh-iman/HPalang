/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration;

import HPalang.Core.ContinuousExpression;
import HPalang.Core.ContinuousVariable;
import HPalang.Core.Equalitable;

/**
 *
 * @author Iman Jahandideh
 */
public class Reset extends Equalitable<Reset>
{
    private final ContinuousVariable variable;
    private final ContinuousExpression expression;
    
    public Reset(ContinuousVariable variable, ContinuousExpression expression)
    {
        this.variable = variable;
        this.expression = expression;
    }

    @Override
    protected boolean InternalEquals(Reset other)
    {
        return this.variable.equals(other.variable) && this.expression.equals(other.expression);
    }

    @Override
    protected int InternalHashCode()
    {
        return variable.hashCode()+ expression.hashCode();
    }
}
