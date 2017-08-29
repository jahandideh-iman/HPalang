/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.Labels;

import HPalang.Core.ContinuousExpression;
import HPalang.Core.ContinuousVariable;
import HPalang.Core.Equalitable;
import HPalang.Core.Expression;
import HPalang.Core.Statement;
import HPalang.Core.Variable;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

/**
 *
 * @author Iman Jahandideh
 */
public class Reset extends Equalitable<Reset>
{
    private final Variable variable;
    private final Expression expression;
    
    public Reset(Variable variable, Expression expression)
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
    
    public Variable Variable()
    {
        return variable;
    }
    
    static public Set<Reset> From(Reset ...resets)
    {
        return new LinkedHashSet<>(Arrays.asList(resets));
    }
}
