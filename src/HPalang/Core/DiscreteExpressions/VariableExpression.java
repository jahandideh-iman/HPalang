/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Core.DiscreteExpressions;

import HPalang.Core.Expression;
import HPalang.Core.ExpressionVisitor;
import HPalang.Core.ValuationContainers.SimpleValuationContainer;
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
    
    public Variable Variable()
    {
        return variable;
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
        return valuations.Has(variable);
    }    

    @Override
    public String toString()
    {
        return variable.Name();
    }
    
    @Override
    public void Visit(ExpressionVisitor visitor)
    {
        visitor.Visit(this);
    }
    
}
