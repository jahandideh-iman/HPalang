/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Core;

import java.util.HashSet;
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
        
        return params.equals(parameters.AsSet());
    }
    
    public VariableArgument ArgumentFor(VariableParameter parameter)
    {
       for(VariableArgument arg : arguments)
           if(arg.Parameter().equals(parameter))
               return arg;
       return null;
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
    
    public Set<VariableArgument> AsSet()
    {
        return new HashSet<>(arguments);
    }
    
    public static MessageArguments From(VariableArgument ... args)
    {
        MessageArguments arguments = new MessageArguments();
        
        for(VariableArgument arg : args)
            arguments.Add(arg);
        
        return arguments;
    }
    
    
    public static MessageArguments Empty()
    {
        return MessageArguments.From();
    }
}
