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
public class DefferentialEquation extends Equalitable<DefferentialEquation>
{
    private final ContinuousVariable variable;
    private final String equation;
    
    public DefferentialEquation(ContinuousVariable variable, String equation)
    {
        this.variable = variable;
        this.equation = equation;
    }

    @Override
    protected boolean InternalEquals(DefferentialEquation other)
    {
        return variable.equals(other.variable)
                && equation.equals(other.equation);
    }

    @Override
    protected int InternalHashCode()
    {
        return variable.hashCode() + equation.hashCode();
    }

    public String GetEquation()
    {
        return equation;
    }

    public ContinuousVariable GetVariable()
    {
        return variable;
    }
    
    static public DefferentialEquation Empty(String eq)
    {
        return new DefferentialEquation(new ContinuousVariable("empty"), eq);
    }
}
