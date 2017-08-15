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
public class DifferentialEquation extends Equalitable<DifferentialEquation>
{
    private final ContinuousVariable variable;
    private final String equation;
    
    public DifferentialEquation(ContinuousVariable variable, String equation)
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

    public String GetEquation()
    {
        return equation;
    }

    public ContinuousVariable GetVariable()
    {
        return variable;
    }
    
    static public DifferentialEquation Empty(String eq)
    {
        return new DifferentialEquation(new ContinuousVariable("empty"), eq);
    }
    
    static public DifferentialEquation Empty()
    {
        return new DifferentialEquation(new ContinuousVariable("empty"), "empty");
    }
}
