/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration;

import HPalang.Core.ContinuousExpressions.ConstantContinuousExpression;
import HPalang.Core.ContinuousExpressions.DifferentialEquation;
import HPalang.Core.ContinuousExpressions.Invarient;
import HPalang.Core.DiscreteExpressions.BinaryExpression;
import HPalang.Core.DiscreteExpressions.ConstantDiscreteExpression;
import HPalang.Core.DiscreteExpressions.FalseConst;
import HPalang.Core.DiscreteExpressions.TrueConst;
import HPalang.Core.DiscreteExpressions.VariableExpression;
import HPalang.Core.Expression;
import HPalang.LTSGeneration.Labels.Guard;
import HPalang.LTSGeneration.Labels.Reset;
import java.util.Stack;

/**
 *
 * @author Iman Jahandideh
 */
public class BasicPostorderExpressionCrawler extends PostorderExpressionCrawler
{

    private Stack<Expression> expressions;
    
    protected final void InitStack()
    {
        expressions = new Stack<>();
    }

    protected Expression Result()
    {
        if(expressions.size() != 1)
            assert (false);
        assert (expressions.size() == 1);
        return Pop();
    }
            
    protected final void Push(Expression expr)
    {
        expressions.push(expr);
    }
    
    protected final Expression Pop()
    {
        return expressions.pop();
    }
    
    @Override
    protected void Process(VariableExpression expr)
    {
        Push(expr);
    }

    @Override
    protected void Process(ConstantContinuousExpression expr)
    {
        Push(expr);
    }

    @Override
    protected void Process(ConstantDiscreteExpression expr)
    {
        Push(expr);
    }

    @Override
    protected void Process(BinaryExpression expr)
    {
        Pop();
        Pop();
        Push(expr);
    }

    @Override
    protected void Process(TrueConst expr)
    {
        Push(expr);
    }

    @Override
    protected void Process(FalseConst expr)
    {
        Push(expr);
    }

    @Override
    protected void Process(Expression expr)
    {
        Push(expr);
    }

    @Override
    protected void Process(Invarient expr)
    {
        Pop();
        Push(expr);
    }

    @Override
    protected void Process(Guard expr)
    {
        Pop();
        Push(expr);
    }

    @Override
    protected void Process(DifferentialEquation expr)
    {
        Pop();
        Push(expr);
    }

    @Override
    protected void Process(Reset expr)
    {
        Pop();
        Push(expr);
    }
    
}
