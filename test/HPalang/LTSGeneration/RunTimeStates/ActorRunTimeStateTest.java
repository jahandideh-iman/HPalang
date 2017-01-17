/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.RunTimeStates;

import HPalang.LTSGeneration.Utilities;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

/**
 *
 * @author Iman Jahandideh
 */
public class ActorRunTimeStateTest
{
    @Test
    public void DeepCopyIsCorrect()
    {
        ActorRunTimeState original = Utilities.NewActorState("actor");
        
        ActorRunTimeState copy = original.DeepCopy();
        
        assertThat(copy.getClass(),equalTo(original.getClass()));
        
        assertThat(copy.GetActor(), sameInstance(original.GetActor()));
        
        assertEqualButNotSame(copy, original);
        assertEqualButNotSame(copy.LowPriorityMessageQueue(), original.LowPriorityMessageQueue());
        assertEqualButNotSame(copy.HighPriorityMessageQueue(), original.HighPriorityMessageQueue());
        assertEqualButNotSame(copy.ContinuousBehaviors(), original.ContinuousBehaviors());
        assertEqualButNotSame(copy.StatementQueue(), original.StatementQueue());
        assertEqualButNotSame(copy.Valuations(), original.Valuations());
    }
    
    private void assertEqualButNotSame(Object obj1,Object obj2)
    {
        assertThat(obj1, equalTo(obj2));
        assertThat(obj1, not(sameInstance(obj2)));
    }
}
