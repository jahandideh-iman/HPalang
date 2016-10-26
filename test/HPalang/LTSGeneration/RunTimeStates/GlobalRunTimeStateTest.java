/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.RunTimeStates;

import HPalang.Core.Actor;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

/**
 *
 * @author Iman Jahandideh
 */
public class GlobalRunTimeStateTest
{
    @Test
    public void CloneIsCorrectForEmptyState()
    {
        GlobalRunTimeState orignal = new GlobalRunTimeState();
        
        GlobalRunTimeState copy = orignal.Clone();
        
        assertTrue(copy.equals(orignal));
        assertFalse(copy == orignal);
        assertTrue(copy.getClass() == orignal.getClass());
    }
    
    @Test
    public void CloneCreateNewActorStateForNotEmptyState()
    {
        Actor actor = new Actor("",0);
        GlobalRunTimeState orignalGlobalState = new GlobalRunTimeState();
        ActorRunTimeState orignalActorState = new ActorRunTimeState(actor);
        
        orignalGlobalState.AddActorRunTimeState(orignalActorState);
        
        GlobalRunTimeState copyGlobalState = orignalGlobalState.Clone();
        
        ActorRunTimeState copyActorState = copyGlobalState.FindActorState(actor);
        
        assertFalse(copyActorState == orignalActorState);
    }
    
    @Test
    public void CloneIsCorrectForNotEmptyState()
    {
        Actor actor1 = new Actor("1",0);
        Actor actor2 = new Actor("2",0);
        
        ActorRunTimeState actorState1 = new ActorRunTimeState(actor1);
        ActorRunTimeState actorState2 = new ActorRunTimeState(actor2);
        
        GlobalRunTimeState orignal = new GlobalRunTimeState();
        orignal.AddActorRunTimeState(actorState1);
        orignal.AddActorRunTimeState(actorState2);
        
        GlobalRunTimeState copy = orignal.Clone();
        
        assertTrue(copy.equals(orignal));
        assertFalse(copy == orignal);
        assertTrue(copy.getClass() == orignal.getClass());
        assertThat(copy.GetActorStates().size(),is(2));
    }
}
