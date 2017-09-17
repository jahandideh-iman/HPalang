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
import HPalang.Core.Variables.RealVariable;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
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
        List<RealVariable> variables = Arrays.asList(new RealVariable("var"));
        
        Statement statement1 = new MessageTeardownStatement(parameters, variables);
        Statement statement2 = new MessageTeardownStatement(parameters, variables);
        
        assertThat(statement1, is(equalTo(statement2)));
    }
    
    @Test
    public void StatementsWithNotEqualMessageParametersAreNotEqual()
    {
        MessageParameters parameters = ParametersFrom("var");
        MessageParameters anotherParameters = ParametersFrom("anotherVar");   
        List<RealVariable> variables = Arrays.asList(new RealVariable("var"));
        
        assertThat(parameters, not(equalTo(anotherParameters)));
        
        Statement statement1 = new MessageTeardownStatement(parameters, variables);
        Statement statement2 = new MessageTeardownStatement(anotherParameters, variables);
        
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
