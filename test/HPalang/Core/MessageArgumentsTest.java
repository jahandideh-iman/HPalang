/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Core;

import static TestUtilities.NetworkingUtility.*;
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
        VariableArgument arg1 = ArgumentFor(Variable.Type.integer);
        VariableArgument arg2 = ArgumentFor(Variable.Type.floatingPoint);

        MessageArguments arguments1 = new MessageArguments();
        arguments1.Add(arg1);
        arguments1.Add(arg2);
        
        MessageArguments arguments2 = new MessageArguments();
        arguments2.Add(arg1);
        arguments2.Add(arg2);
        
        assertThat(arguments1, is(equalTo(arguments2))); 
    }
    
    @Test
    public void ArgumentsWithNotEqualDataAreNotEqual()
    {
        VariableArgument arg1 = ArgumentFor(Variable.Type.real);
        VariableArgument arg2 = ArgumentFor(Variable.Type.integer);
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
        VariableParameter param1 = Parameter("param1", Variable.Type.integer);
        VariableParameter param2 = Parameter("param2", Variable.Type.floatingPoint);
        
        MessageParameters parameters = new MessageParameters();
        parameters.Add(param1);
        parameters.Add(param2);
        
        MessageArguments arguments =  new MessageArguments();
        arguments.Add(ArgumentFor(param1.Type()));
        arguments.Add(ArgumentFor(param2.Type()));
    
        assertThat(arguments.Match(parameters),is(true)); 
    }
    
    @Test
    public void ArgumentsDoesNotMatchWithDifferentParametersType()
    {
        VariableParameter param = Parameter("param", Variable.Type.integer);
        
        MessageParameters parameters = new MessageParameters();
        parameters.Add(param);
        
        MessageArguments arguments =  new MessageArguments();
        arguments.Add(ArgumentFor(Variable.Type.floatingPoint));
    
        assertThat(arguments.Match(parameters),is(false)); 
    }
}
