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
public class LogicalExpression extends DiscreteExpression<LogicalExpression>
{
    public enum Operator
    {
        Invalid, AND,OR
    };
    
    private final DiscreteExpression operand1;
    private final Operator operator;
    private final DiscreteExpression operand2;
        
    public LogicalExpression(DiscreteExpression operand1, Operator operator, DiscreteExpression operand2)
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
            case AND:
                return ( (operand1.Evaluate(valuations)>0) && (operand2.Evaluate(valuations)>0))? 1:0;
            case OR:
                return ( (operand1.Evaluate(valuations)>0) || (operand2.Evaluate(valuations)>0))? 1:0;
            default:
               throw new RuntimeException("Unsupported Arithmetic Operator.");

        }
    }
    
    @Override
    protected boolean InternalEquals(LogicalExpression other)
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