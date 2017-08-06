/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Core;

import java.util.Queue;
import java.util.LinkedList;


/**
 *
 * @author Iman Jahandideh
 */
public interface Statement
{
    public boolean Is(Class<? extends Statement> cc);
    
    static public Queue<Statement> EmptyStatements()
    {
        return new LinkedList<>();
    }
    static public Queue<Statement> StatementsFrom(Statement ...statements)
    {
        Queue<Statement> queue = new LinkedList<>();
        for(Statement s : statements)
            queue.add(s);
        return queue;
    }
}
