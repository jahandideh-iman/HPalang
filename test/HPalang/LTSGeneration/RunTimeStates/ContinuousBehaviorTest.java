/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.RunTimeStates;

import HPalang.Statements.Statement;
import static org.hamcrest.CoreMatchers.equalTo;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Iman Jahandideh
 */
public class ContinuousBehaviorTest
{   
    @Test
    public void BehaviorsWithDataAreEqual()
    {     
        ContinuousBehavior behavior1 = new ContinuousBehavior("inv", "eq", "g", Statement.EmptyStatements());
        ContinuousBehavior behavior2 = new ContinuousBehavior("inv", "eq", "g", Statement.EmptyStatements());
            
        assertThat(behavior2, equalTo(behavior1));
    }
    
}
