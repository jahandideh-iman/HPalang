/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.RunTimeStates;

import HPalang.Core.Statement;
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
    public void StatesWithEqualBehaviorsAreEqual()
    {
        ContinuousState state1 = new ContinuousState();
        ContinuousState state2 = new ContinuousState();
        
        ContinuousBehavior behavior1 = new ContinuousBehavior("inv1", "eq1", "g1", Statement.EmptyStatements());
        ContinuousBehavior behavior2 = new ContinuousBehavior("inv2", "eq2", "g2", Statement.EmptyStatements());
        
        state1.AddBehavior(behavior1);
        state1.AddBehavior(behavior2);
        
        state2.AddBehavior(behavior1);
        state2.AddBehavior(behavior2);
        
        assertThat(state2, equalTo(state1));
    }
    
    @Test
    public void DeepCopyIsCorrectForEmptyState()
    {
        ContinuousState copy = original.DeepCopy();
        
        assertThat(copy,equalTo(original));
        assertThat(copy,not(sameInstance(original)));
        assertThat(copy.getClass(),equalTo(original.getClass()));
    }
    
    @Test
    public void CloneIsCorrectForContinuousBehavior()
    {
        ContinuousBehavior behavior1 = new ContinuousBehavior("", "", "", Statement.EmptyStatements());
        ContinuousBehavior behavior2 = new ContinuousBehavior("", "", "", Statement.EmptyStatements());
        
        original.AddBehavior(behavior1);
        original.AddBehavior(behavior2);
        
        ContinuousState copy = original.DeepCopy();
        
        assertTrue(copy.equals(original));
    }
    
    @Test
    public void ClonedStateHasSeperateBehaviors()
    {
        ContinuousBehavior behavior1 = new ContinuousBehavior("", "", "", Statement.EmptyStatements());
        ContinuousBehavior behavior2 = new ContinuousBehavior("", "", "", Statement.EmptyStatements());
        
        original.AddBehavior(behavior1);
        original.AddBehavior(behavior2);
        
        ContinuousState copy = original.DeepCopy();
        
        ContinuousBehavior behavior = copy.GetBehaviors().get(0);
        copy.RemoveBehavior(behavior);
        
        assertFalse(copy.equals(original));
        assertThat(copy.GetBehaviors(), not(hasItem(sameInstance(behavior))));
        assertThat(original.GetBehaviors(), hasItem(sameInstance(behavior)));
    }
}
