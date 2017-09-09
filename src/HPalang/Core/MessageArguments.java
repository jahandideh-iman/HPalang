/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Core;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Iman Jahandideh
 */
public class MessageArguments extends Equalitable<MessageArguments>
{
    private final List<VariableArgument> arguments = new LinkedList<>();
    
    public void Add(VariableArgument argument)
    {
        arguments.add(argument);
    }
    
    public boolean Match(MessageParameters parameters)
    {
        List<VariableParameter> paramsList = parameters.AsList();
        
        if(paramsList.size() != arguments.size())
            return false;
        
        for(int i = 0 ; i< arguments.size(); i++)
            if(DoesNotHaveSameType(arguments.get(i), paramsList.get(i)))
                return false;
        return true;
        
    }
    
    private boolean DoesNotHaveSameType(VariableArgument argument, VariableParameter parameter)
    {
        return argument.Type().equals(parameter.Type()) == false;
    }
//    private VariableArgument ArgumentFor(VariableParameter parameter)
//    {
//       for(VariableArgument arg : arguments)
//           if(arg.Parameter().equals(parameter))
//               return arg;
//       return null;
//    }

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
    
    public List<VariableArgument> AsList()
    {
        return arguments;
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
