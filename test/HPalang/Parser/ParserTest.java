/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Parser;

import HPalang.Core.SoftwareActor;
import HPalang.Core.ContinuousExpressions.ConstantContinuousExpression;
import HPalang.Core.DifferentialEquation;
import HPalang.Core.Messages.NormalMessage;
import HPalang.Core.Statements.ContinuousAssignmentStatement;
import HPalang.Core.Statements.SendStatement;
import java.io.IOException;
import static org.hamcrest.CoreMatchers.*;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;

/**
 *
 * @author Iman Jahandideh
 */
public class ParserTest extends ParserTestBase
{

    @Test
    public void ParsesMultipleActor() throws IOException
    {
        input = CreateInput(""
                + "actor A{} "
                + "actor B{} "
        );
        
        model = parser.ParseModel(input);
        
        assertThat(model.FindActor("A"),is(notNullValue()));
        assertThat(model.FindActor("B"),is(notNullValue()));       
    }
    
    @Test @Ignore
    public void ParsesActorVariableDefinitions() throws IOException
    {
        input = CreateInput(""
                + "actor A {"
                + "     int dVar1;"
                + "     int dVar2;"
                + "} "
        );
        
        model = parser.ParseModel(input);
        
        SoftwareActor actor = model.FindActor("A");
        
        assertThat(actor.Type().HasVariable("dVar1"),is(true));  
        assertThat(actor.Type().HasVariable("dVar2"),is(true)); 

    }
    
    @Test @Ignore
    public void ParsesEmptyMessageHandlers() throws IOException
    {
        input = CreateInput(""
                + "actor A {"
                + "     handler1(){}"
                + "     handler2(){}"
                + "} "
        );
        
        model = parser.ParseModel(input);
        
        SoftwareActor actor = model.FindActor("A");
        
        assertThat(actor.Type().FindMessageHandler("handler1"),is(notNullValue()));  
        assertThat(actor.Type().FindMessageHandler("handler2"),is(notNullValue())); 
    }
    
    @Test @Ignore
    public void ParsesSendMessage() throws IOException
    {
        input = CreateInput(""
                + "actor A {"
                + "     a1(){"
                + "         B.b1();"
                + "     }"
                + "} "
                + "actor B {"
                + "     b1(){}"
                + "}"
        );
        
        model = parser.ParseModel(input);
        
        SoftwareActor actorA = model.FindActor("A");
        SoftwareActor actorB = model.FindActor("B");
        
        SendStatement sendStat = GetFirstStatement(actorA.Type().FindMessageHandler("a1"));
        NormalMessage message = (NormalMessage)sendStat.GetMessage();
        
        assertThat(sendStat.GetReceiver(),is(actorB));  
        assertThat(message.GetMessageHandler(),is(actorB.Type().FindMessageHandler("b1"))); 
    }
    
//    @Test
//    public void ParsesComparisionGuardAndInvarient() throws IOException
//    {
//        input = CreateInput(""
//                + "actor A {"
//                + "     real var;"
//                + "     a1(){"
//                + "         inv(var <= 2.0)"
//                + "         { var' = 2.0}"
//                + "         guard(var == 2.0)"
//                + "         {};"
//                + "     }"
//                + "} "
//        );
//        
//        model = parser.ParseModel(input);
//        
//        SoftwareActor actorA = model.FindActor("A");
//        
//        ContinuousBehaviorStatement statement = GetFirstStatement(actorA.GetMessageHandler("a1"));
//          
//        assertThat(statement.GetBehavior().GetInvarient(), is(equalTo("var<=2.0")));
//        assertThat(statement.GetBehavior().GetGuard(), is(equalTo("var==2.0")));
//    }
}
