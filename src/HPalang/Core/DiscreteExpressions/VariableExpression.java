/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Core.DiscreteExpressions;

import HPalang.Core.DiscreteExpression;
import HPalang.Core.DiscreteVariable;
import HPalang.Core.ValuationContainer;

/**
 *
 * @author Iman Jahandideh
 */
public class VariableExpression extends DiscreteExpression<VariableExpression>
{
    private final DiscreteVariable variable;
    
    public VariableExpression(DiscreteVariable variable)
    {
        this.variable = variable;
    }
    
    @Override
    public int Evaluate(ValuationContainer valuations)
    {
        return valuations.Get(variable);
    }
    
    @Override
    protected boolean InternalEquals(VariableExpression other)
    {
        return this.variable.equals(other.variable);
    }

    @Override
    protected int InternalHashCode()
    {
        return variable.hashCode();
    }
    
}
