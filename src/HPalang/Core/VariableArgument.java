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
public class VariableArgument  extends Equalitable<VariableArgument>
{
    private final VariableParameter parameter;
    private final Expression value;
    
    public VariableArgument(VariableParameter parameter, Expression value)
    {
        this.parameter = parameter;
        this.value = value;
    }
    
    public VariableParameter Parameter()
    {
        return parameter;
    }
    
    @Override
    protected boolean InternalEquals(VariableArgument other)
    {
        return this.parameter.equals(other.parameter) &&
                this.value.equals(other.value);
    }

    @Override
    protected int InternalHashCode()
    {
        return 0;
    }
    
}
