/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Core;

import HPalang.Core.Variables.IntegerVariable;
import Mocks.NullExpression;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Iman Jahandideh
 */
public class MessageArgumentsTest
{
    @Test
    public void ArgumentsWithEqualDataAreEqual()
    {
        VariableArgument arg1 = ArgumentFor(ParameterFor("param1"));
        VariableArgument arg2 = ArgumentFor(ParameterFor("param2"));

        MessageArguments arguments1 = new MessageArguments();
        arguments1.Add(arg1);
        arguments1.Add(arg2);
        
        MessageArguments arguments2 = new MessageArguments();
        arguments2.Add(arg2);
        arguments2.Add(arg1);
        
        assertThat(arguments1, is(equalTo(arguments2))); 
    }
    
    @Test
    public void ArgumentsWithNotEqualDataAreNotEqual()
    {
        VariableArgument arg1 = ArgumentFor(ParameterFor("param1"));
        VariableArgument arg2 = ArgumentFor(ParameterFor("param2"));
        assertThat(arg2, is(not(equalTo(arg1))));

        MessageArguments arguments1 = new MessageArguments();
        arguments1.Add(arg1);
        
        MessageArguments arguments2 = new MessageArguments();
        arguments2.Add(arg2);
        
        assertThat(arguments1, is(not(equalTo(arguments2)))); 
    }
    
    @Test
    public void ArgumentsMatchWithSameParameters()
    {
        VariableParameter param1 = ParameterFor("param1");
        VariableParameter param2 = ParameterFor("param2");
        
        MessageParameters parameters = new MessageParameters();
        parameters.Add(param1);
        parameters.Add(param2);
        
        MessageArguments arguments =  new MessageArguments();
        arguments.Add(ArgumentFor(param1));
        arguments.Add(ArgumentFor(param2));
    
        assertThat(arguments.Match(parameters),is(true)); 
    }
    
    @Test
    public void ArgumentsDoesNotMatchWithDifferentParameters()
    {
        VariableParameter param = ParameterFor("param");
        VariableParameter anotherParam = ParameterFor("anotherParam");
        assertThat(param, is(not(equalTo(anotherParam))));
        
        MessageParameters parameters = new MessageParameters();
        parameters.Add(param);
        
        MessageArguments arguments =  new MessageArguments();
        arguments.Add(ArgumentFor(anotherParam));
    
        assertThat(arguments.Match(parameters),is(false)); 
    }
    
    private VariableArgument ArgumentFor(VariableParameter parameter)
    {
        return new VariableArgument(parameter, new NullExpression());
    }
    
    private VariableParameter ParameterFor(String param)
    {
        return new VariableParameter(new IntegerVariable(param));
    }
}
