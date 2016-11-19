/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.RunTimeStates;

import HPalang.Core.Message;
import HPalang.Core.Statement;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Iman Jahandideh
 */
public class DiscreteState implements AbstractState<DiscreteState>
{
    private Queue<Message> messages = new LinkedList<>();
    private Queue<Statement> statements = new LinkedList<>();
    private boolean isDelayed = false;

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

    public void RemoveMessage(Message message)
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
    
    public void EnqueueStatements(Queue<Statement> statements)
    {
        for(Statement statement : statements)
            EnqueueStatement(statement);
    }

    public Queue<Statement> GetStatements()
    {
        return statements;
    }

    public boolean HasStatement()
    {
        return statements.isEmpty() == false;
    }
    
        public void SetDelayed(boolean delayed)
    {
        isDelayed = delayed;
    }
    
    public boolean IsDelayed()
    {
        return isDelayed;
    }

    @Override
    public DiscreteState DeepCopy()
    {
        try {
            DiscreteState copy = (DiscreteState) clone();
            copy.messages = copy.messages.getClass().newInstance();
            for(Message m : messages)
                copy.EnqueueMessage(m);
            
            copy.statements = copy.statements.getClass().newInstance();
            for(Statement s : statements)
                copy.EnqueueStatement(s);
            
            return copy;
        } catch (CloneNotSupportedException | InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(DiscreteState.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    @Override
    public boolean equals(Object other)
    {
         if(other == null)
            return false;
        
        if (!DiscreteState.class.isAssignableFrom(other.getClass()))
            return false;
            
        DiscreteState otherState = (DiscreteState) other;
        
        return Arrays.equals(this.messages.toArray(), otherState.messages.toArray())
                && Arrays.equals(this.statements.toArray(),otherState.statements.toArray())
                && this.isDelayed == otherState.isDelayed; 
    }

    @Override
    public int hashCode()
    {
        int hash = 5;
        hash = 17 * hash + Objects.hashCode(this.messages);
        hash = 17 * hash + Objects.hashCode(this.statements);
        return hash;
    }
}

