/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration;

import HPalang.LTSGeneration.RunTimeStates.GlobalRunTimeState;
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
    
}
