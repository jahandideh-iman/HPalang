/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Convertors;

import HPalang.Core.DiscreteExpressions.BinaryExpression;
import HPalang.Core.DiscreteExpressions.BinaryOperators.LogicalAndOperator;
import HPalang.Core.DiscreteExpressions.BinaryOperators.LogicalOrOperator;
import HPalang.Core.Expression;
import HPalang.LTSGeneration.BasicPostOrderExpressionConvertor;
import HPalang.LTSGeneration.Labels.Guard;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 *
 * @author Iman Jahandideh
 */
public class DisjunctiveNormalFormConvertor extends BasicPostOrderExpressionConvertor
{

    @Override
    protected void Process(BinaryExpression expr)
    {
        if(expr.Operator() instanceof LogicalAndOperator)
        {
            Expression operand2 =  Pop();
            Expression operand1 = Pop();
            
            if(IsDisjunction(operand1) || IsDisjunction(operand2))
            {
                Push(ConjunctiveDistributionFor(operand1, operand2));
            }
            else
                Push(new BinaryExpression(operand1, expr.Operator(), operand2));
            
            
        }
        else
            super.Process(expr);
        
    }

    private boolean IsDisjunction(Expression expr)
    {
        if(expr instanceof BinaryExpression)
            return ((BinaryExpression)expr).Operator() instanceof LogicalOrOperator;
        else
            return false;
    }
    
     private boolean IsConjunction(Expression expr)
    {
        if(expr instanceof BinaryExpression)
            return ((BinaryExpression)expr).Operator() instanceof LogicalAndOperator;
        else
            return false;
    }

    private Expression ConjunctiveDistributionFor(Expression expr1, Expression expr2)
    {
        Expression result;
        if(IsDisjunction(expr1) && IsDisjunction(expr2))
        {
            throw  new UnsupportedOperationException();
        }
        else if(IsDisjunction(expr1) && !IsDisjunction(expr2))
        {
            BinaryExpression bExpr1 = (BinaryExpression) expr1;
            
            Expression resOperand1 = new BinaryExpression(bExpr1.Operand1(), new LogicalAndOperator(), expr2);
            Expression resOperand2 = new BinaryExpression(bExpr1.Operand2(), new LogicalAndOperator(), expr2);
            
             return new BinaryExpression(resOperand1, new LogicalOrOperator() ,resOperand2);
        }
        else if(!IsDisjunction(expr1) && IsDisjunction(expr2))
        {
            BinaryExpression bExpr2 = (BinaryExpression) expr2;
            
            Expression resOperand1 = new BinaryExpression(expr1, new LogicalAndOperator(), bExpr2.Operand1());
            Expression resOperand2 = new BinaryExpression(expr1, new LogicalAndOperator(), bExpr2.Operand2());
            
             return new BinaryExpression(resOperand1, new LogicalOrOperator() ,resOperand2);
        }
        else
        {
            throw new RuntimeException("It is a forbbiden state");
        }
    }

    Expression Convert(Expression expr)
    {
        InitStack();
        expr.Visit(this);
        return Result();
    }


    
}
