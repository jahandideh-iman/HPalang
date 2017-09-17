/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Core.Messages;

import HPalang.Core.Message;
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
        Queue<Statement> statements = Statement.StatementsFrom(new EmptyStatement(),new EmptyStatement());
        
        Message.MessageType messageType = Message.MessageType.Control;
        
        MessageWithBody message1 = new MessageWithBody(statements, messageType);
        MessageWithBody message2 = new MessageWithBody(statements, messageType);
        
        assertThat(message2, equalTo(message1));   
    }
    
    @Test
    public void MessageWithNotEqualStatementsAreNotEqual()
    {
        Statement statement1 = new EmptyStatement();
        Statement statement2 = new DelayStatement(1f);
        
        Message.MessageType messageType = Message.MessageType.Control;
       
        MessageWithBody message1 = new MessageWithBody(Statement.StatementsFrom(statement1, statement2),messageType);
        MessageWithBody message2 = new MessageWithBody(Statement.StatementsFrom(statement2, statement1),messageType);
        
        assertThat(message2, not(equalTo(message1)));   
    }
    
}
