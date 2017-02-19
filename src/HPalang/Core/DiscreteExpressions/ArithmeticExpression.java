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
public class ArithmeticExpression extends DiscreteExpression<ArithmeticExpression>
{  
    public enum Operator
    {
        Invalid, Add,Subtract
    };
    
    private final DiscreteExpression operand1;
    private final Operator operator;
    private final DiscreteExpression operand2;
        
    public ArithmeticExpression(DiscreteExpression operand1, Operator operator, DiscreteExpression operand2)
    {
        this.operand1 = operand1;
        this.operator = operator;
        this.operand2 = operand2;
    }

    @Override
    public int Evaluate(ValuationContainer valuations)
    {
        switch (operator) 
        {
            case Subtract:
                return operand1.Evaluate(valuations) - operand2.Evaluate(valuations);
            case Add:
                return operand1.Evaluate(valuations) + operand2.Evaluate(valuations);
            default:
               throw new RuntimeException("Unsupported Arithmetic Operator.");
        }
    }

    @Override
    protected boolean InternalEquals(ArithmeticExpression other)
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
