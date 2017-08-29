/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.RunTimeStates.Event;

import HPalang.Core.ContinuousVariable;
import HPalang.Core.Variables.RealVariable;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import Mocks.EmptyAction;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Iman Jahandideh
 */
public class EventTest
{
    
    @Test
    public void EventsWithEqualDataAreEqual()
    {
        float delay = 5f;
        RealVariable timer = new RealVariable("timer");
        Action action = new EmptyAction();
        
        Event event1 = new Event(delay, timer, action);      
        Event event2 = new Event(delay, timer, action);
        
        assertThat(event1,is(equalTo(event2)));
    }
    
}
