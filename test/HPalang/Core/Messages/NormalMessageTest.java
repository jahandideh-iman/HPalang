/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Core.Messages;

import HPalang.Core.Message;
import HPalang.Core.MessageHandler;
import HPalang.Core.Statement;
import HPalang.Core.Statements.DelayStatement;
import Mocks.EmptyStatement;
import java.util.Queue;
import static org.hamcrest.CoreMatchers.equalTo;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Iman Jahandideh
 */
public class NormalMessageTest
{
    
    @Test
    public void MessagesWithEqualMessageHandlersAreEqual()
    {
        MessageHandler messageHandler = new MessageHandler(Message.MessageType.Control);
        
        NormalMessage message1 = new NormalMessage(messageHandler);
        NormalMessage message2 = new NormalMessage(messageHandler);
        
        assertThat(message2, equalTo(message1));   
    }
    
}
