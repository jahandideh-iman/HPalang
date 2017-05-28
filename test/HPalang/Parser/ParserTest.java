/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Parser;

import HPalang.Core.Actor;
import HPalang.Core.ContinuousExpressions.ConstantContinuousExpression;
import HPalang.Core.DefferentialEquation;
import HPalang.Core.Messages.NormalMessage;
import HPalang.Core.Statements.ContinuousAssignmentStatement;
import HPalang.Core.Statements.ContinuousBehaviorStatement;
import HPalang.Core.Statements.SendStatement;
import java.io.IOException;
import static org.hamcrest.CoreMatchers.*;
import org.junit.Test;
import static org.junit.Assert.*;

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
    
    @Test
    public void ParsesActorVariableDefinitions() throws IOException
    {
        input = CreateInput(""
                + "actor A {"
                + "     int dVar1;"
                + "     int dVar2;"
                + "     real cVar1;"
                + "     real cVar2;"
                + "} "
        );
        
        model = parser.ParseModel(input);
        
        Actor actor = model.FindActor("A");
        
        assertThat(actor.HasDiscreteVariable("dVar1"),is(true));  
        assertThat(actor.HasDiscreteVariable("dVar2"),is(true)); 
        
        assertThat(actor.HasContinuousVariable("cVar1"),is(true));  
        assertThat(actor.HasContinuousVariable("cVar2"),is(true)); 
    }
    
    @Test
    public void ParsesEmptyMessageHandlers() throws IOException
    {
        input = CreateInput(""
                + "actor A {"
                + "     handler1(){}"
                + "     handler2(){}"
                + "} "
        );
        
        model = parser.ParseModel(input);
        
        Actor actor = model.FindActor("A");
        
        assertThat(actor.GetMessageHandler("handler1"),is(notNullValue()));  
        assertThat(actor.GetMessageHandler("handler2"),is(notNullValue())); 
    }
    
    @Test
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
        
        Actor actorA = model.FindActor("A");
        Actor actorB = model.FindActor("B");
        
        SendStatement sendStat = GetFirstStatement(actorA.GetMessageHandler("a1"));
        NormalMessage message = (NormalMessage)sendStat.GetMessage();
        
        assertThat(sendStat.GetReceiver(),is(actorB));  
        assertThat(message.GetMessageHandler(),is(actorB.GetMessageHandler("b1"))); 
    }
    
    @Test
    public void ParsesConstantContinuousAssignment() throws IOException
    {
        input = CreateInput(""
                + "actor A {"
                + "     real var;"
                + "     a1(){"
                + "         var = 2.0;"
                + "     }"
                + "} "
        );
        
        model = parser.ParseModel(input);
        
        Actor actorA = model.FindActor("A");
        
        ContinuousAssignmentStatement assignment = GetFirstStatement(actorA.GetMessageHandler("a1"));
        ConstantContinuousExpression constExpr = (ConstantContinuousExpression) assignment.Expression();

        
        assertThat( assignment.Variable(),is(actorA.FindContinuousVariable("var")));  
        assertThat(constExpr.Value(),is(equalTo(2.0f))); 
    }
    
    @Test
    public void ParsesContinuousBehaviorStatement() throws IOException
    {
        input = CreateInput(""
                + "actor A {"
                + "     real var;"
                + "     a1(){"
                + "         inv(true)"
                + "         { var' = 2.0}"
                + "         guard(false)"
                + "         {};"
                + "     }"
                + "} "
        );
        
        model = parser.ParseModel(input);
        
        Actor actorA = model.FindActor("A");
        
        ContinuousBehaviorStatement statement = GetFirstStatement(actorA.GetMessageHandler("a1"));
        DefferentialEquation equ = statement.GetBehavior().GetEquation();
        
        assertThat(statement,is(notNullValue()));  
        assertThat(statement.GetBehavior().GetInvarient(), is(equalTo("true")));
        assertThat(statement.GetBehavior().GetGuard(), is(equalTo("false")));
        assertThat(equ.GetVariable(), is(actorA.FindContinuousVariable("var")));
        assertThat(equ.GetEquation(), is(equalTo("2.0")));
    }
    
    @Test
    public void ParsesComparisionGuardAndInvarient() throws IOException
    {
        input = CreateInput(""
                + "actor A {"
                + "     real var;"
                + "     a1(){"
                + "         inv(var <= 2.0)"
                + "         { var' = 2.0}"
                + "         guard(var == 2.0)"
                + "         {};"
                + "     }"
                + "} "
        );
        
        model = parser.ParseModel(input);
        
        Actor actorA = model.FindActor("A");
        
        ContinuousBehaviorStatement statement = GetFirstStatement(actorA.GetMessageHandler("a1"));
          
        assertThat(statement.GetBehavior().GetInvarient(), is(equalTo("var<=2.0")));
        assertThat(statement.GetBehavior().GetGuard(), is(equalTo("var==2.0")));
    }
}
