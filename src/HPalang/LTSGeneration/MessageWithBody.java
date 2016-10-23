/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration;

import HPalang.Statements.Statement;
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
}
