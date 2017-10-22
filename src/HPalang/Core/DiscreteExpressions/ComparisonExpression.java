/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Core.DiscreteExpressions;

import HPalang.Core.DiscreteExpression;
import HPalang.Core.DiscreteExpressions.BinaryOperators.EqualityOperator;
import HPalang.Core.DiscreteExpressions.BinaryOperators.GreaterEqualOperator;
import HPalang.Core.DiscreteExpressions.BinaryOperators.GreaterOperator;
import HPalang.Core.DiscreteExpressions.BinaryOperators.LesserEqualOperator;
import HPalang.Core.DiscreteExpressions.BinaryOperators.LesserOperator;
import HPalang.Core.Expression;
import HPalang.Core.ExpressionVisitor;
import HPalang.Core.ValuationContainers.SimpleValuationContainer;
import HPalang.Core.ValuationContainer;

/**
 *
 * @author Iman Jahandideh
 */
@Deprecated // Use BinaryExpression instead
public class ComparisonExpression extends DiscreteExpressionT<ComparisonExpression>
{


    public enum Operator
    {
        Invalid, Greater,GreaterEqual, Equal, Lesser, LesserEqual
    };
    
    private final BinaryExpression expression;
        
    public ComparisonExpression(DiscreteExpression operand1, Operator operator, DiscreteExpression operand2)
    {
        switch (operator) {
            case Greater:
                expression = new BinaryExpression(operand1, new GreaterOperator(), operand2);
                break;
            case GreaterEqual:
                expression = new BinaryExpression(operand1, new GreaterEqualOperator(), operand2);
                break;
            case Equal:
                expression = new BinaryExpression(operand1, new EqualityOperator(), operand2);
                break;
            case Lesser:
                expression = new BinaryExpression(operand1, new LesserOperator(), operand2);
                break;
            case LesserEqual:
                expression = new BinaryExpression(operand1, new LesserEqualOperator(), operand2);
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
    protected boolean InternalEquals(ComparisonExpression other)
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
