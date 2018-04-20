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
import HPalang.Core.ContinuousExpressions.Invarient;
import HPalang.Core.Variable;
import HPalang.Core.VariableVisitor;
import HPalang.Core.Variables.FloatVariable;
import HPalang.Core.Variables.IntegerVariable;
import HPalang.Core.Variables.RealVariable;
import HPalang.LTSGeneration.Labels.Guard;
import HPalang.LTSGeneration.Labels.Reset;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

/**
 *
 * @author Iman Jahandideh
 */
public class ExpressionScopeUnwrapper  extends PostorderExpressionCrawler
{
    private String scopeName;
    
    private Stack<Expression> expressoins;
    private Collection<Variable> unWrappableVariables;

    private class VariableScopeChanger implements VariableVisitor
    {

        private Variable convertedVariable;
        private String scopeName;
        
        public Variable Convert(Variable variable, String scopeName)
        {
            this.scopeName = scopeName;
            
            variable.Visit(this);
            
            return convertedVariable;
        }
        
        private String ScopeName(String name)
        {
            return String.format("%s_%s", scopeName, name);
        }
        @Override
        public void Visit(IntegerVariable var)
        {
            convertedVariable = new IntegerVariable(ScopeName(var.Name()));
        }

        @Override
        public void Visit(FloatVariable var)
        {
            convertedVariable = new FloatVariable(ScopeName(var.Name()));
        }

        @Override
        public void Visit(RealVariable var)
        {
            convertedVariable = new RealVariable(ScopeName(var.Name()));
        }

        @Override
        public void Visit(Variable var)
        {
            // This method is only for Mocked Variables, so it doesn't change the variable.
            convertedVariable = var;
        }
    }
//    
//    public Expression Unwrap(Expression expr, String scopeName)
//    {
//        return this.Unwrap(expr, scopeName, Collections.EMPTY_LIST);
//    }
    
    public Expression Unwrap(Expression expr, String scopeName, Collection<Variable> unWrappableVariables)
    {
        this.scopeName = scopeName;
        this.unWrappableVariables = unWrappableVariables;
        this.expressoins = new Stack<>();
        
        expr.Visit(this);
        
        assert (expressoins.size() == 1);
        
        return expressoins.pop();
    }
    
    public Variable Unwrap(Variable var, String scopeName, Collection<Variable> unWrappableVariables)
    {
        if(unWrappableVariables.contains(var))
            return new VariableScopeChanger().Convert(var, scopeName);
        return var;
    }
    
    
    @Override
    protected void Process(VariableExpression expr)
    {
        Variable convertedVariable = expr.Variable();
        if(unWrappableVariables.contains(expr.Variable()))
            convertedVariable= new VariableScopeChanger().Convert(expr.Variable(), scopeName);
        expressoins.push(new VariableExpression(convertedVariable));
    }

    @Override
    protected void Process(ConstantContinuousExpression expr)
    {
        expressoins.push(expr);
    }

    @Override
    protected void Process(ConstantDiscreteExpression expr)
    {
        expressoins.push(expr);
    }

    @Override
    protected void Process(BinaryExpression expr)
    {
        Expression operan2 = expressoins.pop();
        Expression operan1 = expressoins.pop();
        expressoins.push(new BinaryExpression(operan1, expr.Operator(), operan2) );
    }

    @Override
    protected void Process(TrueConst expr)
    {
        expressoins.push(expr);
    }

    @Override
    protected void Process(FalseConst expr)
    {
        expressoins.push(expr);
    }
    
    @Override
    protected void Process(Expression expr)
    {
        expressoins.push(expr);
    }
   
    @Override
    protected void Process(Invarient expr)
    {
        Expression expression = expressoins.pop();
        expressoins.push(new Invarient(expression));
    }
    
    @Override
    protected void Process(Guard expr)
    {
        Expression expression = expressoins.pop();
        expressoins.push(new Guard(expression));
    }

    @Override
    protected void Process(DifferentialEquation expr)
    {
        Expression expression = expressoins.pop();
        expressoins.push(new DifferentialEquation(
                (RealVariable)Unwrap(
                        expr.GetVariable(), 
                        scopeName,
                        unWrappableVariables)
                ,expression));
    }
    
    @Override
    protected void Process(Reset expr)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
