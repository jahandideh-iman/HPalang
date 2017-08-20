/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Parser;

import HPalang.Core.SoftwareActor;
import HPalang.Core.ContinuousExpressions.ConstantContinuousExpression;
import HPalang.Core.DiscreteExpressions.ConstantDiscreteExpression;
import HPalang.Core.Expression;
import HPalang.Core.MessageHandler;
import HPalang.Core.Statements.ContinuousAssignmentStatement;
import HPalang.Core.Statements.DiscreteAssignmentStatement;
import java.io.IOException;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Ignore;
import org.junit.Test;

/**
 *
 * @author Iman Jahandideh
 */
public class ParserAssignmentTest extends ParserTestBase
{
    @Test @Ignore
    public void PrasesDiscreteAssignment() throws IOException
    {
        input = CreateInput(""
                + "actor A {"
                + "     int var;"
                + "     a1(){"
                + "         var = 1;"
                + "     }"
                + "} "
        );
        
        model = parser.ParseModel(input);
        
        SoftwareActor actorA = model.FindActor("A");
        MessageHandler handler =actorA.Type().FindMessageHandler("a1"); 
        
        DiscreteAssignmentStatement assignment = GetStatement(0,handler);
        
        Expression expected = new ConstantDiscreteExpression(1);
        
        assertThat( assignment.Variable(),is(actorA.Type().FindVariable("var")));
        assertThat(assignment.Expression(), is(equalTo(expected)));
    }
    
}
