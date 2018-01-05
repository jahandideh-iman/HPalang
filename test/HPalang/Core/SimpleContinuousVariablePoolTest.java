/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Core;

import HPalang.Core.Variables.RealVariable;
import static org.hamcrest.CoreMatchers.*;
import org.hamcrest.core.IsNull;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Iman Jahandideh
 */
public class SimpleContinuousVariablePoolTest
{
    @Test
    public void PoolsWithEqualDataAreEqual()
    {
        SimpleRealVariablePool pool1 = new SimpleRealVariablePool(5);      
        SimpleRealVariablePool pool2 = new SimpleRealVariablePool(5);
        
        assertThat(pool1,is(equalTo(pool2)));
    }
    
    @Test
    public void PoolsWithNotEqualDataAreEqual()
    {
        SimpleRealVariablePool pool1 = new SimpleRealVariablePool(5);      
        SimpleRealVariablePool pool2 = new SimpleRealVariablePool(13);
        
        assertThat(pool1,is(not(equalTo(pool2))));
    }
    
    
    @Test(expected = RuntimeException.class)
    public void IfPoolIsEmptyWhenAcquiredThenThrowsExpection()
    {
        SimpleRealVariablePool pool = new SimpleRealVariablePool(0);      

        RealVariable var = pool.Acquire();
        
    }
    
    @Test
    public void IfPoolIsNotEmptyWhenAcquiredThenReturnAndRemovesAVariable()
    {
        SimpleRealVariablePool pool = new SimpleRealVariablePool(5);      

        RealVariable var = pool.Acquire();
        
        assertThat(var,is(not(nullValue())));        
        assertThat(pool.Has(var),is(equalTo(false)));
        assertThat(pool.Size(), is(equalTo(4)));
    }
    
    @Test
    public void WhenAVariableIsReleasedThenAddsItTooThePool()
    {
        SimpleRealVariablePool pool = new SimpleRealVariablePool(5);
        RealVariable var = pool.Acquire();
        
        pool.Release(var);
              
        assertThat(pool.Has(var),is(equalTo(true)));
        assertThat(pool.Size(), is(equalTo(5)));
    }
}
