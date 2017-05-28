/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Core.DiscreteExpressions;

import HPalang.Core.DiscreteExpression;
import HPalang.Core.ValuationContainer;

/**
 *
 * @author Iman Jahandideh
 */
public class ComparisonExpression extends DiscreteExpressionT<ComparisonExpression>
{
    public enum Operator
    {
        Invalid, Greater,GreaterEqual, Equal, Lesser, LesserEqual
    };
    
    private final DiscreteExpression operand1;
    private final Operator operator;
    private final DiscreteExpression operand2;
        
    public ComparisonExpression(DiscreteExpression operand1, Operator operator, DiscreteExpression operand2)
    {
        this.operand1 = operand1;
        this.operator = operator;
        this.operand2 = operand2;
    }

    @Override
    public int Evaluate(ValuationContainer valuations)
    {
        switch (operator) {
            case Greater:
                return (operand1.Evaluate(valuations) > operand2.Evaluate(valuations)) ? 1 : 0;
            case GreaterEqual:
                return (operand1.Evaluate(valuations) >= operand2.Evaluate(valuations)) ? 1 : 0;
            case Equal:
                return (operand1.Evaluate(valuations) == operand2.Evaluate(valuations)) ? 1 : 0;
            case Lesser:
                return (operand1.Evaluate(valuations) < operand2.Evaluate(valuations)) ? 1 : 0;
            case LesserEqual:
                return (operand1.Evaluate(valuations) <= operand2.Evaluate(valuations)) ? 1 : 0;
            default:
               throw new RuntimeException("Unsupported Arithmetic Operator.");

        }
    }
    
    @Override
    protected boolean InternalEquals(ComparisonExpression other)
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
}
