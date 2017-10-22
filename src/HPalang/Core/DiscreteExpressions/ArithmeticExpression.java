/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Core.DiscreteExpressions;

import HPalang.Core.DiscreteExpression;
import HPalang.Core.DiscreteExpressions.BinaryOperators.AddOperator;
import HPalang.Core.DiscreteExpressions.BinaryOperators.SubtractOperator;
import HPalang.Core.Expression;
import HPalang.Core.ExpressionVisitor;
import HPalang.Core.ValuationContainers.SimpleValuationContainer;
import HPalang.Core.ValuationContainer;

/**
 *
 * @author Iman Jahandideh
 */
@Deprecated // Use Binary Expression instead
public class ArithmeticExpression extends DiscreteExpressionT<ArithmeticExpression>
{  

    public enum Operator
    {
        Invalid, Add,Subtract
    };
     
    private final BinaryExpression expression;
        
    public ArithmeticExpression(DiscreteExpression operand1, Operator operator, DiscreteExpression operand2)
    {
        if(operator == Operator.Add)
            expression = new BinaryExpression(operand1, new AddOperator(), operand2);
        else if(operator == Operator.Subtract)
            expression = new BinaryExpression(operand1, new SubtractOperator(), operand2);
        else 
            expression = null;
    }

    @Override
    public int Evaluate(ValuationContainer valuations)
    {
        return expression.Evaluate(valuations);
    }

    @Override
    protected boolean InternalEquals(ArithmeticExpression other)
    {
        return this.expression.equals(other.expression);
    }

    @Override
    protected int InternalHashCode()
    {
        return 0;
    }
    
    @Override
    public boolean IsComputable(ValuationContainer valuations)
    {
        return expression.IsComputable(valuations);
    }

    @Override
    public Expression PartiallyEvaluate(ValuationContainer valuations)
    {
        return expression.PartiallyEvaluate(valuations);
    }
    
    
    @Override
    public void Visit(ExpressionVisitor visitor)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
