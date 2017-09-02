/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Core.DiscreteExpressions.BinaryOperators;

import HPalang.Core.DiscreteExpression;
import HPalang.Core.SimpleValuationContainer;
import HPalang.Core.ValuationContainer;
import HPalang.Core.Expression;

/**
 *
 * @author Iman Jahandideh
 */
public class LesserOperator extends BinaryOperatorT<LesserOperator>
{

    @Override
    public int Evaluate(Expression operand1, Expression operand2, ValuationContainer valuations)
    {
        return (operand1.Evaluate(valuations) < operand2.Evaluate(valuations)) ? 1 : 0;
    }
    
}
