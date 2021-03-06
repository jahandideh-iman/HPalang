/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Core.DiscreteExpressions.BinaryOperators;

import HPalang.Core.Expression;
import HPalang.Core.ValuationContainer;

/**
 *
 * @author Iman Jahandideh
 */
public class DivisionOperator extends BinaryOperatorT<DivisionOperator>
{

    @Override
    public int Evaluate(Expression operand1, Expression operand2, ValuationContainer valuations)
    {
        return operand1.Evaluate(valuations) / operand2.Evaluate(valuations);
    }
    
    @Override
    public String toString()
    {
        return "/";
    }
}
