/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.RunTimeStates;

import HPalang.LTSGeneration.ContinuousBehavior;
import HPalang.LTSGeneration.Message;
import HPalang.Statements.Statement;
import Mocks.EmptyMessage;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.*;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Iman Jahandideh
 */
public class ContinuousStateTest
{
    
    ContinuousState original = new ContinuousState();
    
    @Test
    public void CloneIsCorrectForEmptyState()
    {
        ContinuousState copy = original.Clone();
        
        assertTrue(copy.equals(original));
        assertFalse(copy == original);
        assertTrue(copy.getClass() == original.getClass());
    }
    
    @Test
    public void CloneIsCorrectForContinuousBehavior()
    {
        ContinuousBehavior behavior1 = new ContinuousBehavior("", "", "", null);
        ContinuousBehavior behavior2 = new ContinuousBehavior("", "", "", null);
        
        original.AddBehavior(behavior1);
        original.AddBehavior(behavior2);
        
        ContinuousState copy = original.Clone();
        
        assertTrue(copy.equals(original));
    }
    
    @Test
    public void ClonedStateHasSeperateBehaviors()
    {
        ContinuousBehavior behavior1 = new ContinuousBehavior("", "", "", null);
        ContinuousBehavior behavior2 = new ContinuousBehavior("", "", "", null);
        
        original.AddBehavior(behavior1);
        original.AddBehavior(behavior2);
        
        ContinuousState copy = original.Clone();
        
        ContinuousBehavior behavior = copy.GetBehaviors().get(0);
        copy.RemoveBehavior(behavior);
        
        assertFalse(copy.equals(original));
        assertThat(copy.GetBehaviors(), not(hasItem(behavior)));
        assertThat(original.GetBehaviors(), hasItem(behavior));
    }
}
