/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.RunTimeStates;

import HPalang.Core.Message;
import HPalang.Core.Statement;
import HPalang.LTSGeneration.SimpleState;
import HPalang.Utilities.Queue;

/**
 *
 * @author Iman Jahandideh
 */
public class ExecutionQueueState extends SimpleState<ExecutionQueueState>
{
    private final  Queue<Statement> statements = new Queue<>();
    
    public Queue<Statement> Statements()
    {
        return statements;
    }
    
    @Override
    protected ExecutionQueueState NewInstance()
    {
        return new ExecutionQueueState();
    }

    @Override
    protected boolean DataEquals(ExecutionQueueState other)
    {
        return statements.equals(other.statements);
    }

    @Override
    protected void CloneData(ExecutionQueueState copy)
    {
        copy.statements.Enqueue(statements);
    }

    @Override
    protected int InternalHashCode()
    {
        return statements.hashCode();
    } 
}
