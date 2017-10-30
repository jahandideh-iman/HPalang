/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration;

import HPalang.Core.ContinuousExpressions.ConstantContinuousExpression;
import HPalang.Core.ContinuousExpressions.DifferentialEquation;
import HPalang.Core.DiscreteExpressions.BinaryExpression;
import HPalang.Core.DiscreteExpressions.ConstantDiscreteExpression;
import HPalang.Core.DiscreteExpressions.FalseConst;
import HPalang.Core.DiscreteExpressions.TrueConst;
import HPalang.Core.DiscreteExpressions.VariableExpression;
import HPalang.Core.Expression;
import HPalang.Core.Expressions.Visitors.*;
import HPalang.Core.ContinuousExpressions.Invarient;
import HPalang.Core.Visitor;
import HPalang.LTSGeneration.Labels.Guard;
import HPalang.LTSGeneration.Labels.GuardVisitor;
import HPalang.LTSGeneration.Labels.Reset;
import HPalang.LTSGeneration.Labels.ResetVisitor;

/**
 *
 * @author Iman Jahandideh
 */
public abstract class PostorderExpressionCrawler implements 
        Visitor, 
        VariableExpressionVisitor,
        ConstantContinuousExpressionVisitor,
        ConstantDiscreteExpressionVisitor,
        TrueConstVisitor,
        FalseConstVisitor,
        BinaryExpressionVisitor,
        InvarientVisitor,
        GuardVisitor,
        DifferentialEquationVisitor,
        ResetVisitor,
        GeneralExpressionVisitor
{

    @Override
    public void Visit(VariableExpression expr)
    {
        Process(expr);
    }
    
    abstract protected void Process(VariableExpression expr);

    @Override
    public void Visit(ConstantContinuousExpression expr)
    {
        Process(expr);
    }
    
    abstract protected void Process(ConstantContinuousExpression expr);

    @Override
    public void Visit(ConstantDiscreteExpression expr)
    {
        Process(expr);
    }
    
    abstract protected void Process(ConstantDiscreteExpression expr);
    
    @Override
    public void Visit(BinaryExpression expr)
    {
        expr.Operand1().Visit(this);
        expr.Operand2().Visit(this);
        Process(expr);
    }
    
    abstract protected void Process(BinaryExpression expr);
    
    @Override
    public void Visit(TrueConst expr)
    {
        Process(expr);
    }
    
    abstract protected void Process(TrueConst expr);

    @Override
    public void Visit(FalseConst expr)
    {
        Process(expr);
    }
    
    abstract protected void Process(FalseConst expr);

    @Override
    public void Visit(Expression expr)
    {
        Process(expr);
    }

    abstract protected void Process(Expression expr);   

    @Override
    public void Visit(Invarient expr)
    {
        expr.Expression().Visit(this);
        Process(expr);
    }
    
    abstract protected void Process(Invarient expr); 

    @Override
    public void Visit(Guard expr)
    {
        expr.Expression().Visit(this);
        Process(expr);
    }
    
    abstract protected void Process(Guard expr); 

    @Override
    public void Visit(DifferentialEquation expr)
    {
        expr.GetEquation().Visit(this);
        Process(expr);
    }
    
    abstract protected void Process(DifferentialEquation expr);

    @Override
    public void Visit(Reset expr)
    {
        expr.Expression().Visit(this);
        Process(expr);
        
    }
    
    abstract protected void Process(Reset expr);
    
}
