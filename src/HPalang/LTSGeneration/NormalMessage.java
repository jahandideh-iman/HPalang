/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration;

import HPalang.Core.Actor;
import HPalang.Core.MessageHandler;
import HPalang.Statements.Statement;
import java.util.Queue;

/**
 *
 * @author Iman Jahandideh
 */
public class NormalMessage implements Message
{
    private final MessageHandler messageHandler;

    public NormalMessage(MessageHandler messageHandler)
    {
        this.messageHandler = messageHandler;
    }
    
    @Override
    public boolean equals(Object other)
    {
        if(other == null)
            return false;
        
        if (!NormalMessage.class.isAssignableFrom(other.getClass()))
            return false;
            
        NormalMessage otherm = (NormalMessage) other;
       
        return otherm.messageHandler.equals(this.messageHandler);
    }
    
    @Override
    public int hashCode()
    {
        return messageHandler.hashCode();
    }

    @Override
    public Queue<Statement> GetMessageBody()
    {
        return messageHandler.GetBody();
    }
}
