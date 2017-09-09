/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Core.DiscreteExpressions;

import HPalang.Core.DiscreteExpression;
import HPalang.Core.Expression;
import HPalang.Core.ValuationContainers.SimpleValuationContainer;
import HPalang.Core.ValuationContainer;

/**
 *
 * @author Iman Jahandideh
 */
public class BinaryExpression extends DiscreteExpressionT<BinaryExpression>
{  
    private final Expression operand1;
    private final BinaryOperator operator;
    private final Expression operand2;

    public BinaryExpression(Expression operand1, BinaryOperator operator,Expression operand2)
    {
        this.operand1 = operand1;
        this.operator = operator;
        this.operand2 = operand2;
    }
    
    @Override
    public int Evaluate(ValuationContainer valuations)
    {
        return operator.Evaluate(operand1, operand2, valuations);
    }
        
    @Override
    protected boolean InternalEquals(BinaryExpression other)
    {
        return this.operand1.equals(other.operand1)
                && this.operator.equals(other.operator)
                && this.operand2.equals(other.operand2);
    }

    @Override
    protected int InternalHashCode()
    {
        return operand1.hashCode() + operator.hashCode()+ operand2.hashCode();
    }   

    @Override
    public boolean IsComputable(ValuationContainer valuations)
    {
        return operand1.IsComputable(valuations) && operand2.IsComputable(valuations);
    }

    @Override
    public Expression PartiallyEvaluate(ValuationContainer valuations)
    {
        return new BinaryExpression(operand1.PartiallyEvaluate(valuations), operator, operand2.PartiallyEvaluate(valuations));
    }
}
