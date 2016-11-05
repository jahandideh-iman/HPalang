/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Core;

import HPalang.Statements.Statement;
import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author Iman Jahandideh
 */
public class MessageHandler
{
    private final Queue<Statement> statements = new LinkedList<>();
    private Actor owner;
    private String id;
    
    public MessageHandler()
    {
    }
    
    public void SetOwner(Actor owner)
    {
        this.owner = owner;
    }
    
    public void AddStatement(Statement statement)
    {
        statements.add(statement);
    }
    
    public Queue<Statement> GetBody()
    {
        return statements;
    }
    
    public void SetID(String id)
    {
        this.id = id;
    }

    public String GetID()
    {
       return id;
    }
}
