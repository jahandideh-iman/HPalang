/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Statements;

import HPalang.Core.DefferentialEquation;
import HPalang.Core.Statements.ContinuousBehaviorStatement;
import HPalang.Core.Statement;
import HPalang.LTSGeneration.RunTimeStates.ContinuousBehavior;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

/**
 *
 * @author Iman Jahandideh
 */
public class ContinuousBehaviorStatementTest
{
    
    @Test
    public void StatementsWithEqualBehaviorsAreEqual()
    {
        ContinuousBehavior behavior = new ContinuousBehavior("inv", DefferentialEquation.Empty("eq"), "guard", Statement.EmptyStatements());
        
        ContinuousBehaviorStatement statement1 = new ContinuousBehaviorStatement(behavior);
        ContinuousBehaviorStatement statement2 = new ContinuousBehaviorStatement(behavior);
        
        assertThat(statement2, equalTo(statement1));
    }
    
}
