/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Statements;

import HPalang.Core.Statements.DelayStatement;
import static org.hamcrest.CoreMatchers.equalTo;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Iman Jahandideh
 */
public class DelayStatementTest
{
    @Test
    public void  StatementsWithEqualDelayAreEqual()
    {
        DelayStatement statement1 = new DelayStatement(1.5f);
        DelayStatement statement2 = new DelayStatement(1.5f);

        assertThat(statement2, equalTo(statement1));        
    }
    
}
