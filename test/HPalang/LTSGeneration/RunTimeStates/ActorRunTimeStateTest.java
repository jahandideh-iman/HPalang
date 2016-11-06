/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.RunTimeStates;

import HPalang.Core.Actor;
import HPalang.LTSGeneration.Utilities;
import Mocks.EmptyMessage;
import Mocks.EmptyStatement;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
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
        
        assertThat(copy, equalTo(original));
        assertThat(copy, not(sameInstance(original)));
        assertThat(copy.getClass(),equalTo(original.getClass()));
        assertThat(copy.GetActor(), sameInstance(original.GetActor()));
    }
    
    @Test
    public void DeepCopyCreatesNewDiscreteAndContiniuousState()
    {
        ActorRunTimeState original = Utilities.NewActorState("actor");
        ActorRunTimeState copy = original.DeepCopy();
        
        assertThat(copy.GetContinuousState(),not(sameInstance(original.GetContinuousState())));
        assertThat(copy.GetContinuousState(),equalTo(original.GetContinuousState()));
        
        assertThat(copy.GetDiscreteState(),not(sameInstance(original.GetDiscreteState())));
        assertThat(copy.GetDiscreteState(),equalTo(original.GetDiscreteState()));
        
    }
      
}
