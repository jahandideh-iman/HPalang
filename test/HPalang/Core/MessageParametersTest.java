/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Core;

import HPalang.Core.Variables.IntegerVariable;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Iman Jahandideh
 */
public class MessageParametersTest
{
    @Test
    public void ParametersWithEqualDataAreEqual()
    {
        VariableParameter param1 = ParameterFor("param1");
        VariableParameter param2 = ParameterFor("param2");

        MessageParameters parameters1 = new MessageParameters();
        parameters1.Add(param1);
        parameters1.Add(param2);
        
        MessageParameters parameters2 = new MessageParameters();
        parameters2.Add(param1);
        parameters2.Add(param2);
        
        assertThat(parameters1, is(equalTo(parameters2))); 
    }
    
    @Test
    public void ParametersWithNotEqualDataAreNotEqual()
    {
        VariableParameter param = ParameterFor("param");
        VariableParameter anotherParam = ParameterFor("anotherParam");
        assertThat(param, not(equalTo(anotherParam)));
        
        MessageParameters parameters = new MessageParameters();
        parameters.Add(param);
        
        MessageParameters anotherParameters = new MessageParameters();
        anotherParameters.Add(anotherParam);
        
        
        assertThat(parameters, not(equalTo(anotherParameters))); 
    }
    
    
    private VariableParameter ParameterFor(String param)
    {
        return new VariableParameter(new IntegerVariable(param));
    }
}
