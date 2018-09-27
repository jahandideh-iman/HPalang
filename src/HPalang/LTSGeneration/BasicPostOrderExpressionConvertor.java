/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration;

import HPalang.Core.ContinuousExpressions.DifferentialEquation;
import HPalang.Core.ContinuousExpressions.Invarient;
import HPalang.Core.DiscreteExpressions.BinaryExpression;
import HPalang.Core.Expression;
import HPalang.LTSGeneration.Labels.Guard;
import HPalang.LTSGeneration.Labels.Reset;

/**
 *
 * @author Iman Jahandideh
 */
public class BasicPostOrderExpressionConvertor extends BasicPostorderExpressionCrawler
{

    @Override
    protected void Process(BinaryExpression expr)
    {
        Expression operand2 =  Pop();
        Expression operand1 = Pop();
        Push(new BinaryExpression(operand1, expr.Operator(), operand2));
    }

    @Override
    protected void Process(DifferentialEquation expr)
    {
        Expression e = Pop();
        Push(new DifferentialEquation(expr.GetVariable(), e));
    }

    @Override
    protected void Process(Reset expr)
    {
        Expression e =  Pop();
        Push(new Reset(expr.Variable(), e));
    }

    @Override
    protected void Process(Guard expr)
    {
        Expression e = Pop();
        Push(new Guard(e));
    }

    @Override
    protected void Process(Invarient expr)
    {
        Expression e = Pop();
        Push(new Invarient(e));
    }
}
