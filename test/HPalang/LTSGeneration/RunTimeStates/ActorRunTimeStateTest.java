/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.RunTimeStates;

import HPalang.Core.Actor;
import HPalang.LTSGeneration.Message;
import HPalang.Statements.Statement;
import Mocks.EmptyMessage;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.junit.Before;

/**
 *
 * @author Iman Jahandideh
 */
public class ActorRunTimeStateTest
{

    Actor actor;
    ActorRunTimeState original;
    
    @Before
    public void Setup()
    {
        actor = new Actor("", 0);
        original = new ActorRunTimeState(actor);
    }
    
    
    @Test
    public void CloneIsCorrectForEmptyState()
    {
        ActorRunTimeState copy = original.Clone();
        
        assertTrue(copy.equals(original));
        assertFalse(copy == original);
        assertTrue(copy.getClass() == original.getClass());
        assertTrue(copy.GetActor() == actor);
    }
    
    @Test
    public void CloneCreatesNewDiscreteAndContiniuousState()
    {
        ActorRunTimeState copy = original.Clone();
        
        assertFalse(copy.GetContinuousState() == original.GetContinuousState());
        assertFalse(copy.GetDiscreteState() == original.GetDiscreteState());
    }    
}
