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
public interface BinaryOperator 
{
    int Evaluate(DiscreteExpression op1,DiscreteExpression op2 ,ValuationContainer valuations);
}
