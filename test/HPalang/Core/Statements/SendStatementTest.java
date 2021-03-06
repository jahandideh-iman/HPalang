/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Core.Statements;

import HPalang.Core.ActorLocator;
import HPalang.Core.Message;
import HPalang.Core.MessageArguments;
import HPalang.Core.MessageLocator;
import HPalang.Core.Messages.MessageWithBody;
import HPalang.Core.Statements.SendStatement;
import HPalang.Core.SoftwareActor;
import HPalang.Core.Statements.SendStatement.ArgumentsDoesNotMatchException;
import HPalang.Core.Variable;
import HPalang.Core.VariableArgument;
import HPalang.Core.VariableParameter;
import HPalang.Core.Variables.FloatVariable;
import HPalang.Core.Variables.IntegerVariable;
import HPalang.Core.ActorLocators.DirectActorLocator;
import HPalang.Core.MessageLocators.DirectMessageLocator;
import Mocks.FakeMessage;
import Mocks.EmptyMessage;
import HPalang.Core.NullExpression;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import org.junit.Test;
import static org.junit.Assert.*;
import static TestUtilities.CoreUtility.*;

/**
 *
 * @author Iman Jahandideh
 */
public class SendStatementTest
{
    SoftwareActor actor = new SoftwareActor("Actor", EmptySoftwareActorType(), 0);
    
    @Test
    public void  StatementsWithEqualDataAreEqual()
    {
        MessageLocator messageLocator = new DirectMessageLocator(new EmptyMessage());
        ActorLocator actorLocator = new DirectActorLocator(actor);
        MessageArguments arguemnts = new MessageArguments();
        
        SendStatement statement1 = new SendStatement(actorLocator, messageLocator, arguemnts);
        SendStatement statement2 = new SendStatement(actorLocator, messageLocator, arguemnts);

        assertThat(statement2, equalTo(statement1));        
    }
    
    @Test(expected = ArgumentsDoesNotMatchException.class)
    public void  RaisesExceptionWhenTheArgumentsDoesNotMatchTheMessageParametersType()
    {
        VariableParameter param = new VariableParameter( new IntegerVariable("var"));
        VariableParameter anotherParam = new VariableParameter( new FloatVariable("anotherVar"));
        assertThat(param.Type(), not(equalTo(anotherParam.Type())));

        FakeMessage message = new FakeMessage();
        message.AddParameter(param);
        MessageLocator messageLocator = new DirectMessageLocator(message);
        
        MessageArguments arguments = new MessageArguments();
        arguments.Add(new VariableArgument(anotherParam.Type(), new NullExpression()));
        
        SendStatement statement = new SendStatement(new DirectActorLocator(actor), messageLocator, arguments);       
    }   
}
