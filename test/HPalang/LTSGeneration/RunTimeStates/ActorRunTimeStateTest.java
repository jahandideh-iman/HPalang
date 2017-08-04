/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.RunTimeStates;

import TestUtilities.Utilities;
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
        ActorRunTimeState original = Utilities.CreateActorState("actor");
        
        ActorRunTimeState copy = (ActorRunTimeState) original.DeepCopy();
        
        assertThat(copy.GetActor(), sameInstance(original.GetActor()));
        
        assertEqualButNotSame(copy, original);
    }
    
    private void assertEqualButNotSame(Object obj1,Object obj2)
    {
        assertThat(obj1, equalTo(obj2));
        assertThat(obj1, not(sameInstance(obj2)));
    }
}
