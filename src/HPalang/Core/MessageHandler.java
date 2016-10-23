/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Core;

import HPalang.Statements.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 *
 * @author Iman Jahandideh
 */
public class MessageHandler
{
    private Queue<Statement> statements = new LinkedList<>();
    private Actor owner;
    
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
}
