/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Core.Messages;

import HPalang.Core.Statement;
import HPalang.Core.Statements.DelayStatement;
import Mocks.EmptyStatement;
import java.util.Queue;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Iman Jahandideh
 */
public class MessageWithBodyTest
{
    
    @Test
    public void MessagesWithEqualStatementQueueAreEqual()
    {
        Queue<Statement> statements1 = Statement.StatementsFrom(new EmptyStatement(),new EmptyStatement());
        Queue<Statement> statements2 = Statement.StatementsFrom(new EmptyStatement(),new EmptyStatement());
        
        MessageWithBody message1 = new MessageWithBody(statements1);
        MessageWithBody message2 = new MessageWithBody(statements2);
        
        assertThat(message2, equalTo(message1));   
    }
    
    @Test
    public void MessagesWithEqualStatementAndDifferentOrderingAreNotEqual()
    {
        Statement statement1 = new EmptyStatement();
        Statement statement2 = new DelayStatement(1f);
       
        MessageWithBody message1 = new MessageWithBody(Statement.StatementsFrom(statement1, statement2));
        MessageWithBody message2 = new MessageWithBody(Statement.StatementsFrom(statement2, statement1));
        
        assertThat(message2, not(equalTo(message1)));   
    }
    
}
