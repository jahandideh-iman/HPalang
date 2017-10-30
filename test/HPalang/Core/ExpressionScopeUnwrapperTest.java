/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Core;

import HPalang.LTSGeneration.ExpressionScopeUnwrapper;
import HPalang.Core.DiscreteExpressions.BinaryExpression;
import HPalang.Core.DiscreteExpressions.BinaryOperators.AddOperator;
import HPalang.Core.DiscreteExpressions.BinaryOperators.EqualityOperator;
import HPalang.Core.DiscreteExpressions.ConstantDiscreteExpression;
import HPalang.Core.DiscreteExpressions.TrueConst;
import HPalang.Core.DiscreteExpressions.VariableExpression;
import HPalang.Core.Variables.IntegerVariable;
import static org.hamcrest.CoreMatchers.*;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Iman Jahandideh
 */
public class ExpressionScopeUnwrapperTest
{


    @Test
    public void IfThereIsNoVariableExpressionResultExpressionIsEqualToInput()
    {
        Expression input = new BinaryExpression(
                new BinaryExpression(Const(1), new AddOperator(), Const(2)),
                new EqualityOperator(),
                new TrueConst());
        
        Expression result = new ExpressionScopeUnwrapper().Unwrap(input, "scopeName");
        
        assertThat(result, equalTo(input));
    }
    
    @Test
    public void UnwrapsTheVariableExpressions()
    {
        Expression input = new BinaryExpression(
                new BinaryExpression(VariableExpression("var1"), new AddOperator(), Const(2)),
                new EqualityOperator(),
                VariableExpression("var2"));
        
        
        Expression expected = new BinaryExpression(
                new BinaryExpression(VariableExpression("scopeName_var1"), new AddOperator(), Const(2)),
                new EqualityOperator(),
                VariableExpression("scopeName_var2"));
        
        Expression result = new ExpressionScopeUnwrapper().Unwrap(input, "scopeName");
        
        assertThat(result, equalTo(expected));
    }
    
    private ConstantDiscreteExpression Const(int value)
    {
        return new ConstantDiscreteExpression(value);
    }
    
    private VariableExpression VariableExpression(String varName)
    {
        return new VariableExpression(new IntegerVariable(varName));
    }
}
