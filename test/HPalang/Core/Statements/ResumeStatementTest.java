/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Core.Statements;

import HPalang.Core.Statements.ResumeStatement;
import static org.hamcrest.CoreMatchers.equalTo;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Iman Jahandideh
 */
public class ResumeStatementTest
{
    @Test
    public void  ResumeStatementsAreEqual()
    {
        ResumeStatement statement1 = new ResumeStatement();
        ResumeStatement statement2 = new ResumeStatement();

        assertThat(statement2, equalTo(statement1));        
    }
    
}
