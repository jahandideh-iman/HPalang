/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Core;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Iman Jahandideh
 */
public class MessageArguments extends Equalitable<MessageArguments>
{
    private final Set<VariableArgument> arguments = new HashSet<>();
    
    public void Add(VariableArgument argument)
    {
        arguments.add(argument);
    }
    
    public boolean Match(MessageParameters parameters)
    {
        Set<VariableParameter> params = new HashSet<>();
        arguments.forEach(a -> params.add(a.Parameter()));
        
        return params.equals(parameters.ParametersSet());
    }

    @Override
    protected boolean InternalEquals(MessageArguments other)
    {
        return this.arguments.equals(other.arguments);
    }

    @Override
    protected int InternalHashCode()
    {
        return 0;
    }
}
