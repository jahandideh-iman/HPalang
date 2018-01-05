/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.RunTimeStates;

import HPalang.Core.ContinuousVariable;
import HPalang.Core.SimpleRealVariablePool;
import HPalang.LTSGeneration.RunTimeStates.Event.Event;
import Mocks.EmptyAction;
import java.util.LinkedList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Iman Jahandideh
 */
public class VariablePoolStateTest
{
    @Test
    public void EventsStateWithEqualDataAreEqual()
    {
        SimpleRealVariablePool pool = new SimpleRealVariablePool(5);

        VariablePoolState state1 = new VariablePoolState(pool);      
        VariablePoolState state2= new VariablePoolState(pool);

        assertThat(state1, is(equalTo(state2)));
    }
    
}
