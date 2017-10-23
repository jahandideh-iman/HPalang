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
public abstract class PostorderExpressionCrawler implements ExpressionVisitor
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
    public void Vist(BinaryExpression expr)
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
    public void Visit(ArithmeticExpression expr)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void Visit(ComparisonExpression expr)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void Visit(LogicalExpression expr)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void Visit(Expression expr)
    {
        Process(expr);
    }

    abstract protected void Process(Expression expr);   
}
