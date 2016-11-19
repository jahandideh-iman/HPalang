/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Statements;

import HPalang.Core.Statements.SendStatement;
import HPalang.Core.Actor;
import Mocks.EmptyMessage;
import static org.hamcrest.CoreMatchers.equalTo;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Iman Jahandideh
 */
public class SendStatementTest
{
    
    @Test
    public void  StatementsWithSameRecieverAndEqualMessagesAreEqual()
    {
        Actor actor = new Actor("Actor", 0);
        
        SendStatement statement1 = new SendStatement(actor, new EmptyMessage());
        SendStatement statement2 = new SendStatement(actor, new EmptyMessage());

        assertThat(statement2, equalTo(statement1));        
    }
    
}
