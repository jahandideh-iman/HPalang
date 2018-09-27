/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.Labels;

import HPalang.Core.Equalitable;
import HPalang.Core.Expression;
import HPalang.Core.ValuationContainer;
import HPalang.Core.Variable;
import HPalang.Core.Visitor;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 *
 * @author Iman Jahandideh
 */
public class Reset extends  Equalitable<Reset> implements Expression
{
    private final Variable variable;
    private final Expression expression;
    
    public Reset(Variable variable, Expression expression)
    {
        this.variable = variable;
        this.expression = expression;
        
        
    }

    @Override
    protected boolean InternalEquals(Reset other)
    {
        return this.variable.equals(other.variable) 
                && this.expression.equals(other.expression);
    }

    @Override
    protected int InternalHashCode()
    {
        return variable.hashCode()+ expression.hashCode();
    }
    
    public Variable Variable()
    {
        return variable;
    }
    
    public Expression Expression()
    {
        return expression;
    }

    @Override
    public String toString()
    {
        return String.format("%s = %s", variable.toString(), expression.toString());
    }
    
    static public Set<Reset> From(Reset ...resets)
    {
        return new LinkedHashSet<>(Arrays.asList(resets));
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
        if(visitor instanceof ResetVisitor)
            ((ResetVisitor)visitor).Visit(this);
    }
    
}
