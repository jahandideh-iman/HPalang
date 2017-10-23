/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Core;

import HPalang.Core.ContinuousExpressions.ConstantContinuousExpression;
import HPalang.Core.DiscreteExpressions.ArithmeticExpression;
import HPalang.Core.DiscreteExpressions.BinaryExpression;
import HPalang.Core.DiscreteExpressions.ComparisonExpression;
import HPalang.Core.DiscreteExpressions.ConstantDiscreteExpression;
import HPalang.Core.DiscreteExpressions.FalseConst;
import HPalang.Core.DiscreteExpressions.LogicalExpression;
import HPalang.Core.DiscreteExpressions.TrueConst;
import HPalang.Core.DiscreteExpressions.VariableExpression;

/**
 *
 * @author Iman Jahandideh
 */
public interface ExpressionVisitor
{
    public void Visit(VariableExpression expr);
    public void Visit(ConstantContinuousExpression expr);
    public void Visit(ConstantDiscreteExpression expr);
    public void Vist(BinaryExpression expr);
    public void Visit(TrueConst expr);
    public void Visit(FalseConst expr);
    
    public void Visit(ComparisonExpression expr);
    public void Visit(ArithmeticExpression expr);
    public void Visit(LogicalExpression expr);
    
    // NOTE: This is only for Mock Expressions.
    public void Visit(Expression expr);
}
