/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Core.Statements;

import HPalang.Core.MessageParameters;
import HPalang.Core.Statement;
import HPalang.Core.Variable;
import HPalang.Core.VariableParameter;
import HPalang.Core.Variables.IntegerVariable;
import java.util.HashSet;
import java.util.Set;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Iman Jahandideh
 */
public class MessageTeardownStatementTest
{
    @Test
    public void StatementsWithEqualDataAreEqual()
    {
        MessageParameters parameters = ParametersFrom("var1", "var2");
        
        Statement statement1 = new MessageTeardownStatement(parameters);
        Statement statement2 = new MessageTeardownStatement(parameters);
        
        assertThat(statement1, is(equalTo(statement2)));
    }
    
    @Test
    public void StatementsWithNotEqualDataAreNotEqual()
    {
        MessageParameters parameters = ParametersFrom("var");
        MessageParameters anotherParameters = ParametersFrom("anotherVar");   
        
        assertThat(parameters, not(equalTo(anotherParameters)));
        
        Statement statement1 = new MessageTeardownStatement(parameters);
        Statement statement2 = new MessageTeardownStatement(anotherParameters);
        
        assertThat(statement1, not(equalTo(statement2)));
    }
    
    private MessageParameters ParametersFrom(String ... paramsName)
    {
        MessageParameters parameters = new MessageParameters();
        
        for(String paramName : paramsName)
            parameters.Add(new VariableParameter(new IntegerVariable(paramName)));
        
        return parameters;
    }
}
