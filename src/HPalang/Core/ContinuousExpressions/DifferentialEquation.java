/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Core.ContinuousExpressions;

import HPalang.Core.ContinuousExpressions.ConstantContinuousExpression;
import HPalang.Core.Equalitable;
import HPalang.Core.Expression;
import HPalang.Core.Expressions.Visitors.DifferentialEquationVisitor;
import HPalang.Core.NullExpression;
import HPalang.Core.ValuationContainer;
import HPalang.Core.Variable;
import HPalang.Core.Variables.RealVariable;
import HPalang.Core.Visitor;

/**
 *
 * @author Iman Jahandideh
 */
public class DifferentialEquation extends Equalitable<DifferentialEquation> implements Expression
{
    private final Variable variable;
    private final Expression equation;
    
    public DifferentialEquation(Variable variable, Expression equation)
    {
        this.variable = variable;
        this.equation = equation;
    }

    @Override
    protected boolean InternalEquals(DifferentialEquation other)
    {
        return variable.equals(other.variable)
                && equation.equals(other.equation);
    }

    @Override
    protected int InternalHashCode()
    {
        return variable.hashCode() + equation.hashCode();
    }

    public Expression GetEquation()
    {
        return equation;
    }

    public Variable GetVariable()
    {
        return variable;
    }
    
    static public DifferentialEquation Empty(String eq)
    {
        return new DifferentialEquation(new RealVariable("empty"), new NullExpression(eq));
    }
    
    static public DifferentialEquation Empty()
    {
        return new DifferentialEquation(new RealVariable("empty"), new ConstantContinuousExpression(0f));
    }

    @Override
    public boolean IsComputable(ValuationContainer valuations)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int Evaluate(ValuationContainer valuations)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Expression PartiallyEvaluate(ValuationContainer valuations)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void Visit(Visitor visitor)
    {
        if(visitor instanceof DifferentialEquationVisitor)
            ((DifferentialEquationVisitor) visitor).Visit(this);
    }
}
