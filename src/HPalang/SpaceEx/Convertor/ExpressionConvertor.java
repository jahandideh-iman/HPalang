/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.SpaceEx.Convertor;

import HPalang.Core.ContinuousExpressions.ConstantContinuousExpression;
import HPalang.Core.ContinuousExpressions.DifferentialEquation;
import HPalang.Core.DiscreteExpressions.BinaryExpression;
import HPalang.Core.DiscreteExpressions.ConstantDiscreteExpression;
import HPalang.Core.DiscreteExpressions.FalseConst;
import HPalang.Core.DiscreteExpressions.TrueConst;
import HPalang.Core.DiscreteExpressions.VariableExpression;
import HPalang.Core.Expression;
import HPalang.Core.ContinuousExpressions.Invarient;
import HPalang.Core.DiscreteExpressions.BinaryOperators.EqualityOperator;
import HPalang.Core.DiscreteExpressions.BinaryOperators.GreaterEqualOperator;
import HPalang.Core.DiscreteExpressions.BinaryOperators.GreaterOperator;
import HPalang.Core.DiscreteExpressions.BinaryOperators.LesserEqualOperator;
import HPalang.Core.DiscreteExpressions.BinaryOperators.LesserOperator;
import HPalang.LTSGeneration.BasicPostorderExpressionCrawler;
import HPalang.LTSGeneration.Labels.Guard;
import HPalang.LTSGeneration.Labels.Reset;
import HPalang.LTSGeneration.PostorderExpressionCrawler;
import java.util.Stack;

/**
 *
 * @author Iman Jahandideh
 */
public class ExpressionConvertor extends PostorderExpressionCrawler
{  
    
    private class GuardConverter extends BasicPostorderExpressionCrawler
    {

        public Expression Convert(Expression expr)
        {
            InitStack();
            
            expr.Visit(this);
            
            return Result();
        }
        @Override
        protected void Process(Guard expr)
        {
            Expression e = Pop();
            if(e instanceof BinaryExpression && ((BinaryExpression)e).Operator() instanceof EqualityOperator )
            {
                BinaryExpression be = (BinaryExpression) e;
                if(be.Operand2() instanceof TrueConst)
                    Push(new Guard(be.Operand1()));
                else if (be.Operand2() instanceof FalseConst)
                    Push(new Guard(Negate(be.Operand1())));
                else
                    Push(expr);
                
            }
            else
                Push(expr);   
        }        

        private Expression Negate(Expression expr)
        {
            return new Negator().Negate(expr);
        }
    }
    
    private class Negator extends BasicPostorderExpressionCrawler
    {

        private Expression Negate(Expression expr)
        {
            InitStack();
            
            expr.Visit(this);
            
            return Result();
        }

        @Override
        protected void Process(BinaryExpression expr)
        {
            Expression operand2 = Pop();
            Expression operand1 = Pop();
            
            if(expr.Operator() instanceof GreaterOperator)
                Push(new BinaryExpression(operand1, new LesserEqualOperator(), operand2));
            else if(expr.Operator() instanceof LesserOperator)
                Push(new BinaryExpression(operand1, new GreaterEqualOperator(), operand2));
            else
                throw new RuntimeException("Operator is not supported yet");
        }
        
        
    }
    private Stack<String> stack = new Stack<>();
    
    public String Convert(Expression expr)
    {
        Expression processExpr = new GuardConverter().Convert(expr);
        processExpr.Visit(this);
        
        assert (stack.size() == 1);
        
        String result = stack.pop();
        
        if(result.startsWith("("))
            result = result.substring(1,result.length()-2);
                
        return result;
    }
    
    private void Push(String str)
    {
        stack.push(str);
    }

    private String Pop()
    {
        return stack.pop();
    }
        
    @Override
    protected void Process(VariableExpression expr)
    {
        Push(expr.Variable().Name());
    }

    @Override
    protected void Process(ConstantContinuousExpression expr)
    {
        Push(String.valueOf(expr.Value()));
    }

    @Override
    protected void Process(ConstantDiscreteExpression expr)
    {
        Push(String.valueOf(expr.Value()));
    }

    @Override
    protected void Process(BinaryExpression expr)
    {
        String secondOperand = Pop(); 
        String firstOperand = Pop(); 
        Push(String.format("( %s %s %s )", firstOperand, expr.Operator().toString(),secondOperand));
    }

    @Override
    protected void Process(TrueConst expr)
    {
        Push("true");
    }

    @Override
    protected void Process(FalseConst expr)
    {
        Push("false");
    }
    
    @Override
    protected void Process(Reset expr)
    {
        String expression = Pop();
        
        Push(String.format("%s := %s", expr.Variable().Name(), expression));
    }
    
    @Override
    protected void Process(Invarient expr)
    {
        
    }

    @Override
    protected void Process(Guard expr)
    {
        
    }

    @Override
    protected void Process(DifferentialEquation expr)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    protected void Process(Expression expr)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
