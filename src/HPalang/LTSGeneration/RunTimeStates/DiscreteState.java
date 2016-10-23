/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.RunTimeStates;

import HPalang.LTSGeneration.Message;
import HPalang.Statements.Statement;
import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author Iman Jahandideh
 */
public class DiscreteState
{
    private Queue<Message> messages = new LinkedList<>();
    private Queue<Statement> statements = new LinkedList<>();

    public Queue<Message> GetMessages()
    {
        return messages;
    }
        
    public void EnqueueMessage(Message message)
    {
        messages.add(message);
    }

    public void DequeueNextMessage()
    {
        messages.poll();
    }

    void RemoveMessage(Message message)
    {
        messages.remove(message);
    }

    public Message GetNextMessage()
    {
        return messages.peek();
    }

    public boolean HasMessage()
    {
        return messages.isEmpty() == false;
    }

    public Statement GetNextStatement()
    {
        return statements.peek();
    }

    public void DequeueNextStatement()
    {
        statements.poll();
    }

    public void EnqueueStatement(Statement statement)
    {
        statements.add(statement);
    }

    public boolean HasStatement()
    {
        return statements.isEmpty() == false;
    }

}

