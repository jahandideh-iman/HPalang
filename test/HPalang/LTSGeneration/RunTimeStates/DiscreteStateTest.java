/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.RunTimeStates;

import Mocks.EmptyMessage;
import Mocks.EmptyStatement;
import static org.hamcrest.CoreMatchers.*;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Iman Jahandideh
 */
public class DiscreteStateTest
{
 
    @Test
    public void StatesWithEqualDataAreEqual()
    {
        DiscreteState state1 = NewFullState();
        DiscreteState state2 = NewFullState();
        
        assertThat(state2,equalTo(state1));
    }

    
    @Test
    public void DeepCopyIsCorrect()
    {
        DiscreteState original = NewFullState();
        DiscreteState copy = original.DeepCopy();
        
        assertThat(copy,equalTo(original));
        assertThat(copy,not(sameInstance(original)));
    }
        
    @Test
    public void DeepCopyCreatesSeperateMessagesAndStatements()
    {
        DiscreteState original = NewFullState();
        DiscreteState copy = original.DeepCopy();
        
        assertThat(copy.GetMessages(),equalTo(original.GetMessages()));
        assertThat(copy.GetMessages(),not(sameInstance(original.GetMessages())));
        
        assertThat(copy.GetStatements(),equalTo(original.GetStatements()));
        assertThat(copy.GetStatements(),not(sameInstance(original.GetStatements())));
    }
     
    private DiscreteState NewFullState()
    {
        DiscreteState state = new DiscreteState();
        
        state.SetDelayed(true);
        state.EnqueueMessage(new EmptyMessage());
        state.EnqueueMessage( new EmptyMessage());
        state.EnqueueStatement(new EmptyStatement());
        state.EnqueueStatement( new EmptyStatement());
        
        return state;
    }
}
