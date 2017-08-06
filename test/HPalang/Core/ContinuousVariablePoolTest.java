/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Core;

import static org.hamcrest.CoreMatchers.*;
import org.hamcrest.core.IsNull;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Iman Jahandideh
 */
public class ContinuousVariablePoolTest
{
    @Test
    public void PoolsWithEqualDataAreEqual()
    {
        ContinuousVariablePool pool1 = new ContinuousVariablePool(5);      
        ContinuousVariablePool pool2 = new ContinuousVariablePool(5);
        
        assertThat(pool1,is(equalTo(pool2)));
    }
    
    @Test
    public void PoolsWithNotEqualDataAreEqual()
    {
        ContinuousVariablePool pool1 = new ContinuousVariablePool(5);      
        ContinuousVariablePool pool2 = new ContinuousVariablePool(13);
        
        assertThat(pool1,is(not(equalTo(pool2))));
    }
    
    
    @Test
    public void IfPoolIsEmptyWhenAcquiredThenReturnNull()
    {
        ContinuousVariablePool pool = new ContinuousVariablePool(0);      

        ContinuousVariable var = pool.Acquire();
        
        assertThat(var,is(nullValue()));
    }
    
    @Test
    public void IfPoolIsNotEmptyWhenAcquiredThenReturnAndRemovesAVariable()
    {
        ContinuousVariablePool pool = new ContinuousVariablePool(5);      

        ContinuousVariable var = pool.Acquire();
        
        assertThat(var,is(not(nullValue())));        
        assertThat(pool.Has(var),is(equalTo(false)));
        assertThat(pool.Size(), is(equalTo(4)));
    }
    
    @Test
    public void WhenAVariableIsReleasedThenAddsItTooThePool()
    {
        ContinuousVariablePool pool = new ContinuousVariablePool(5);
        ContinuousVariable var = pool.Acquire();
        
        pool.Release(var);
              
        assertThat(pool.Has(var),is(equalTo(true)));
        assertThat(pool.Size(), is(equalTo(5)));
    }
}
