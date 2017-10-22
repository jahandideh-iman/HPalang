/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Core.DiscreteExpressions;

import HPalang.Core.DiscreteExpression;
import HPalang.Core.DiscreteExpressions.BinaryOperators.LogicalAndOperator;
import HPalang.Core.DiscreteExpressions.BinaryOperators.LogicalOrOperator;
import HPalang.Core.Expression;
import HPalang.Core.ExpressionVisitor;
import HPalang.Core.ValuationContainers.SimpleValuationContainer;
import HPalang.Core.ValuationContainer;

/**
 *
 * @author Iman Jahandideh
 */
@Deprecated // Use BinaryExpression instead
public class LogicalExpression extends DiscreteExpressionT<LogicalExpression>
{


    public enum Operator
    {
        Invalid, AND,OR
    };
    
    private final BinaryExpression expression;
        
    public LogicalExpression(DiscreteExpression operand1, Operator operator, DiscreteExpression operand2)
    {
        switch (operator) {
            case AND:
                expression = new BinaryExpression(operand1, new LogicalAndOperator(), operand2);
                break;
            case OR:
                expression = new BinaryExpression(operand1, new LogicalOrOperator(), operand2);
                break;
            default:
               expression = null;

        }
    }
    
    @Override
    public int Evaluate(ValuationContainer valuations)
    {
        return expression.Evaluate(valuations);
    }

    @Override
    protected boolean InternalEquals(LogicalExpression other)
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
