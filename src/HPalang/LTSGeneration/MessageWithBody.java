/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration;

import HPalang.Statements.Statement;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;

/**
 *
 * @author Iman Jahandideh
 */
public class MessageWithBody implements Message
{
    private final Queue<Statement> statements;
    
    public MessageWithBody(Queue<Statement> statements)
    {
        this.statements = statements;
    }

    @Override
    public Queue<Statement> GetMessageBody()
    {
        return statements;
    }
    
    @Override
    public boolean equals(Object other)
    {
        if(other == null)
            return false;
        
        if(!this.getClass().isAssignableFrom(other.getClass()))
            return false;
        
        MessageWithBody otherM = (MessageWithBody) other;
        
        return Arrays.equals(statements.toArray(), otherM.statements.toArray());
    }
    
    @Override
    public String toString()
    {
        String out = "{";
        
        for(Statement s : statements)
            out += s.toString() + ",";
        
        return out + "}";
    }
}
