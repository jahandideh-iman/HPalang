/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Core.Statements;

import HPalang.Core.Statement;
import Mocks.EmptyStatement;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import static org.hamcrest.CoreMatchers.equalTo;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Iman Jahandideh
 */
public class AtomicBlockStatementTest
{
    
    @Test
    public void  StatementsWithEqualDelayAreEqual()
    {
        List<Statement> statements = new LinkedList<>(Statement.StatementsFrom(new EmptyStatement("st1"),new EmptyStatement("st2")));
        
        AtomicBlockStatement statement1 = new AtomicBlockStatement(statements);
        AtomicBlockStatement statement2 = new AtomicBlockStatement(statements);

        assertThat(statement2, equalTo(statement1));        
    }
    
}
