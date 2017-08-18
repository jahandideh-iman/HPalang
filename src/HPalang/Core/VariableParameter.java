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
public class VariableParameter extends Equalitable<VariableParameter>
{
    private final Variable variable;
    
    public VariableParameter(Variable variable)
    {
        this.variable = variable;
    }

    @Override
    protected boolean InternalEquals(VariableParameter other)
    {
        return variable.equals(other.variable);
    }
    
    public Variable Variable()
    {
        return variable;
    }

    @Override
    protected int InternalHashCode()
    {
        return 0;
    }

    public String Name()
    {
        return variable.Name();
    }
}
