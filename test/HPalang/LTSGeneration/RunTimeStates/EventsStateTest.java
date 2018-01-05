/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.RunTimeStates;

import HPalang.Core.SimpleRealVariablePool;
import HPalang.Core.Variables.RealVariable;
import HPalang.LTSGeneration.RunTimeStates.Event.Action;
import HPalang.LTSGeneration.RunTimeStates.Event.Event;
import Mocks.EmptyAction;
import Mocks.SingleRealVariablePoolMock;
import static org.hamcrest.CoreMatchers.*;
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
        VariablePoolState poolState1 = new VariablePoolState(new SimpleRealVariablePool(2));
        VariablePoolState poolState2 = new VariablePoolState(new SimpleRealVariablePool(2));
        assertThat(poolState1, equalTo(poolState2));
        
        EventsState state1 = new EventsState();
        state1.AddSubstate(poolState1);
        state1.RegisterEvent(1f, new EmptyAction());
        state1.RegisterEvent(2f, new EmptyAction());      

        
        EventsState state2 = new EventsState();
        state2.AddSubstate(poolState2);
        state2.RegisterEvent(1f, new EmptyAction());
        state2.RegisterEvent(2f, new EmptyAction());     

        assertThat(state1, is(equalTo(state2)));
    }
    
    @Test
    public void AcquiresAVariableForRegisteredEvents()
    {
        RealVariable reservedVar = new RealVariable("reservedVar");
        
        VariablePoolState poolState = new VariablePoolState(new SingleRealVariablePoolMock(reservedVar));
        EventsState eventState = new EventsState();
        eventState.AddSubstate(poolState);
        
        eventState.RegisterEvent(2, new EmptyAction());
        
        Event expectedEvent = new Event(2, reservedVar, new EmptyAction());
        
        assertThat(eventState.Events(), hasItem(expectedEvent));
    }
    
}
