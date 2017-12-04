/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Utilities;

import static org.hamcrest.CoreMatchers.*;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Iman Jahandideh
 */
public class QueueTest
{
    @Test
    public void QueuesWithSameDataAreEqual()
    {
        Queue<Integer> q1 = new Queue<Integer>();
        Queue<Integer> q2 = new Queue<Integer>();
        
        q1.Enqueue(1);
        q1.Enqueue(2);
        
        q2.Enqueue(1);
        q2.Enqueue(2);
        
        assertThat(q1,is(equalTo(q2)));
        assertThat(q1.hashCode(),is(equalTo(q2.hashCode())));
    }
    
}
