/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Core;

/**
 *
 * @author Iman Jahandideh
 */
public class NullExpression extends Equalitable<NullExpression> implements Expression
{
    private final String expression;
    public NullExpression()
    {
        this.expression = "null";
    }
    
    public NullExpression(String expression)
    {
        this.expression = expression;
    }
    
    @Override
    public boolean IsComputable(ValuationContainer valuations)
    {
        return true;
    }

    @Override
    public int Evaluate(ValuationContainer valuations)
    {
        return 0;
    }

    @Override
    public Expression PartiallyEvaluate(ValuationContainer valuations)
    {
        return null;
    }

    @Override
    protected boolean InternalEquals(NullExpression other)
    {
        return this.expression.equals(other.expression);
    }

    @Override
    protected int InternalHashCode()
    {
        return 0;
    }

    @Override
    public void Visit(Visitor visitor)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
