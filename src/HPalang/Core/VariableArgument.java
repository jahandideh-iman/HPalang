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
    private final Expression value;
    private final Variable.Type type;
    
    public VariableArgument(Variable.Type type, Expression value)
    {
        this.value = value;     
        this.type = type;
    }
    
    public Expression Value()
    {
        return value;
    }
    
    @Override
    protected boolean InternalEquals(VariableArgument other)
    {
        return this.type.equals(other.type) &&
                this.value.equals(other.value);
    }

    @Override
    protected int InternalHashCode()
    {
        return 0;
    }

    public Variable.Type Type()
    {
        return type;
    }
    
}
