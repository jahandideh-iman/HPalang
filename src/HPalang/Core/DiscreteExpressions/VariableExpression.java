/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Core.DiscreteExpressions;

import HPalang.Core.Expression;
import HPalang.Core.SimpleValuationContainer;
import HPalang.Core.ValuationContainer;
import HPalang.Core.Variable;
import HPalang.Core.Variables.IntegerVariable;

/**
 *
 * @author Iman Jahandideh
 */
public class VariableExpression extends DiscreteExpressionT<VariableExpression>
{
    private final Variable variable;
    
    public VariableExpression(Variable variable)
    {
        this.variable = variable;
    }

    @Override
    public int Evaluate(ValuationContainer valuations)
    {
        assert (IsComputable(valuations));
        return valuations.ValueFor((IntegerVariable)variable);
    }
    
    @Override
    public Expression PartiallyEvaluate(ValuationContainer valuations)
    {
        assert (! IsComputable(valuations));
        return this;
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

    @Override
    public boolean IsComputable(ValuationContainer valuations)
    {
        return valuations.Has((IntegerVariable)variable);
    }    
}
