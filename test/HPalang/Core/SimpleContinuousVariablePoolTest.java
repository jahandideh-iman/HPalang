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
        SimpleContinuousVariablePool pool1 = new SimpleContinuousVariablePool(5);      
        SimpleContinuousVariablePool pool2 = new SimpleContinuousVariablePool(5);
        
        assertThat(pool1,is(equalTo(pool2)));
    }
    
    @Test
    public void PoolsWithNotEqualDataAreEqual()
    {
        SimpleContinuousVariablePool pool1 = new SimpleContinuousVariablePool(5);      
        SimpleContinuousVariablePool pool2 = new SimpleContinuousVariablePool(13);
        
        assertThat(pool1,is(not(equalTo(pool2))));
    }
    
    
    @Test
    public void IfPoolIsEmptyWhenAcquiredThenReturnNull()
    {
        SimpleContinuousVariablePool pool = new SimpleContinuousVariablePool(0);      

        RealVariable var = pool.Acquire();
        
        assertThat(var,is(nullValue()));
    }
    
    @Test
    public void IfPoolIsNotEmptyWhenAcquiredThenReturnAndRemovesAVariable()
    {
        SimpleContinuousVariablePool pool = new SimpleContinuousVariablePool(5);      

        RealVariable var = pool.Acquire();
        
        assertThat(var,is(not(nullValue())));        
        assertThat(pool.Has(var),is(equalTo(false)));
        assertThat(pool.Size(), is(equalTo(4)));
    }
    
    @Test
    public void WhenAVariableIsReleasedThenAddsItTooThePool()
    {
        SimpleContinuousVariablePool pool = new SimpleContinuousVariablePool(5);
        RealVariable var = pool.Acquire();
        
        pool.Release(var);
              
        assertThat(pool.Has(var),is(equalTo(true)));
        assertThat(pool.Size(), is(equalTo(5)));
    }
}
