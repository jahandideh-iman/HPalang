/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.RunTimeStates;

import HPalang.LTSGeneration.Message;
import HPalang.Statements.Statement;
import Mocks.EmptyMessage;
import java.util.LinkedList;
import java.util.Queue;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.CoreMatchers.not;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Iman Jahandideh
 */
public class DiscreteStateTest
{
    DiscreteState original = new DiscreteState();
    
    @Test
    public void CloneIsCorrectForEmptyState()
    {
        DiscreteState copy = original.Clone();
        
        assertTrue(copy.equals(original));
        assertFalse(copy == original);
        assertTrue(copy.getClass() == original.getClass());
    }
    
    @Test
    public void CloneIsCorrectForDelayedState()
    {
        original.SetDelayed(true);
        
        DiscreteState copy = original.Clone();
        
        assertTrue(copy.equals(original));
        assertThat(copy.IsDelayed(),is(true));
    }
      
    @Test
    public void CloneIsCorrectStateWithMessage()
    {
        Message message1 = new EmptyMessage();
        Message message2 = new EmptyMessage();
        
        original.EnqueueMessage(message1);
        original.EnqueueMessage(message2);
        
        DiscreteState copy = original.Clone();
        
        assertTrue(copy.equals(original));
    }
    
    @Test
    public void ClonedStateHasSeperateMessages()
    {
        Message message1 = new EmptyMessage();
        Message message2 = new EmptyMessage();
        
        original.EnqueueMessage(message1);
        original.EnqueueMessage(message2);
        
        DiscreteState copy = original.Clone();
        
        Message message = copy.GetNextMessage();
        copy.DequeueNextMessage();
        
        assertFalse(copy.equals(original));
        assertThat(copy.GetNextMessage(), not(equalTo(message)));
        assertThat(original.GetNextMessage(), equalTo(message));   
    }
    
    @Test
    public void CloneIsCorrectStateWithStatement()
    {
        Statement statement1 = new Statement();
        Statement statement2 = new Statement();
             
        original.EnqueueStatement(statement1);
        original.EnqueueStatement(statement2);
        
        DiscreteState copy = original.Clone();
        
        assertTrue(copy.equals(original));
    }
    
    @Test
    public void ClonedStateHasSeperateStatements()
    {
        Statement statement1 = new Statement();
        Statement statement2 = new Statement();
        
        original.EnqueueStatement(statement1);
        original.EnqueueStatement(statement2);
        
        DiscreteState copy = original.Clone();
        
        Statement statement = copy.GetNextStatement();
        copy.DequeueNextStatement();
        
        assertFalse(copy.equals(original));
        assertThat(copy.GetNextStatement(), not(sameInstance(statement)));
        assertThat(original.GetNextStatement(), sameInstance(statement));   
    }
}
