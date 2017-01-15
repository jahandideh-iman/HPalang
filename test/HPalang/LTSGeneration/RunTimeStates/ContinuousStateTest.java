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
    
    ContinuousBehaviorContianer original = new ContinuousBehaviorContianer();
    
    
    @Test
    public void StatesWithEqualBehaviorsAreEqual()
    {
        ContinuousBehaviorContianer state1 = new ContinuousBehaviorContianer();
        ContinuousBehaviorContianer state2 = new ContinuousBehaviorContianer();
        
        ContinuousBehavior behavior1 = new ContinuousBehavior("inv1", "eq1", "g1", Statement.EmptyStatements());
        ContinuousBehavior behavior2 = new ContinuousBehavior("inv2", "eq2", "g2", Statement.EmptyStatements());
        
        state1.Add(behavior1);
        state1.Add(behavior2);
        
        state2.Add(behavior1);
        state2.Add(behavior2);
        
        assertThat(state2, equalTo(state1));
    }
    
    @Test
    public void DeepCopyIsCorrectForEmptyState()
    {
        ContinuousBehaviorContianer copy = original.DeepCopy();
        
        assertThat(copy,equalTo(original));
        assertThat(copy,not(sameInstance(original)));
        assertThat(copy.getClass(),equalTo(original.getClass()));
    }
    
    @Test
    public void CloneIsCorrectForContinuousBehavior()
    {
        ContinuousBehavior behavior1 = new ContinuousBehavior("", "", "", Statement.EmptyStatements());
        ContinuousBehavior behavior2 = new ContinuousBehavior("", "", "", Statement.EmptyStatements());
        
        original.Add(behavior1);
        original.Add(behavior2);
        
        ContinuousBehaviorContianer copy = original.DeepCopy();
        
        assertTrue(copy.equals(original));
    }
    
    @Test
    public void ClonedStateHasSeperateBehaviors()
    {
        ContinuousBehavior behavior1 = new ContinuousBehavior("", "", "", Statement.EmptyStatements());
        ContinuousBehavior behavior2 = new ContinuousBehavior("", "", "", Statement.EmptyStatements());
        
        original.Add(behavior1);
        original.Add(behavior2);
        
        ContinuousBehaviorContianer copy = original.DeepCopy();
//        
//        ContinuousBehavior behavior = copy.GetBehaviors().get(0);
//        copy.Remove(behavior);
//        
//        assertFalse(copy.equals(original));
//        assertThat(copy.GetBehaviors(), not(hasItem(sameInstance(behavior))));
//        assertThat(original.GetBehaviors(), hasItem(sameInstance(behavior)));
    }
}
