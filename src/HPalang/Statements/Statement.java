/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Statements;

import HPalang.LTSGeneration.RunTimeStates.ContinuousBehavior;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author Iman Jahandideh
 */
public class Statement
{
    @Override
    public boolean equals(Object other)
    {
        if(other == null)
            return false;
        
        if (!this.getClass().equals(other.getClass()))
            return false;
            
        return true;
    }
    
    static public Queue<Statement> EmptyStatements()
    {
        return new LinkedList<>();
    }
    static public Queue<Statement> StatementsFrom(Statement ...statements)
    {
        Queue<Statement> queue =  new LinkedList<>();
        for(Statement s : statements)
            queue.add(s);
        return queue;
    }
}
