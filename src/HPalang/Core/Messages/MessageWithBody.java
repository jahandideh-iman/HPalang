/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Core.Messages;

import HPalang.Core.Equalitable;
import HPalang.Core.Statement;
import HPalang.Core.Message;
import java.util.Queue;

/**
 *
 * @author Iman Jahandideh
 */
public class MessageWithBody extends Equalitable<MessageWithBody> implements Message
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
    protected boolean InternalEquals(MessageWithBody other)
    {
        return other.statements.equals(this.statements);
    }

    @Override
    protected int InternalHashCode()
    {
        return 1;
    }
    
    @Override
    public String toString()
    {
        String out = "{";
        
        for(Statement s : statements)
            out += s.toString() + ",";
        
        return out + "}";
    }

    @Override
    public void SetPriority(int priority)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int Priority()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
