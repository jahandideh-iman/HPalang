/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.RunTimeStates;

import HPalang.Core.ContinuousVariable;
import HPalang.Core.SimpleContinuousVariablePool;
import HPalang.Core.Variables.RealVariable;
import HPalang.LTSGeneration.RunTimeStates.Event.Event;
import Mocks.EmptyAction;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Iman Jahandideh
 */
public class EventsStateTest
{
    
    @Test
    public void EventsStateWithEqualDataAreEqual()
    {
        VariablePoolState poolState = new VariablePoolState(new SimpleContinuousVariablePool(5));
        Event event1 = new Event(5f,new RealVariable("timer"), new EmptyAction());     
        Event event2 = new Event(5f,new RealVariable("timer2"), new EmptyAction());

        EventsState state1 = new EventsState();
        state1.AddEvent(event1);      
        state1.AddEvent(event2);
        state1.SetPool(poolState);

        
        EventsState state2 = new EventsState();
        state2.AddEvent(event1);      
        state2.AddEvent(event2);
        state2.SetPool(poolState);

        assertThat(state1, is(equalTo(state2)));
    }
    
}
