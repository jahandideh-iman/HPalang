/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Core;

import HPalang.Core.ContinuousExpressions.DifferentialEquation;
import static HPalang.Core.CreationUtilities.CreateTrivialFlaseGuard;
import HPalang.Core.DiscreteExpressions.TrueConst;
import static HPalang.Core.Statement.StatementsFrom;
import HPalang.LTSGeneration.Labels.Guard;
import Mocks.EmptyStatement;
import java.util.Queue;
import java.util.Set;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import org.junit.Test;
import static org.junit.Assert.*;
import static TestUtilities.CoreUtility.*;

/**
 *
 * @author Iman Jahandideh
 */
public class ModeTest
{
    
    @Test
    public void ModesWithEqualDataAreEqual()
    {
        String inv = "inv";
        Set<DifferentialEquation> equations = Mode.EquationsFrom(DifferentialEquation.Empty("eq1"));
        Guard guard = CreateTrivialFlaseGuard();
        Queue<Statement> actions = StatementsFrom(new EmptyStatement());
        
        Mode mode1 = new Mode("mode",TrivialInvarient(), equations , guard, actions);
        Mode mode2 = new Mode("mode",TrivialInvarient(), equations , guard, actions);
        
        assertThat(mode1, is(equalTo(mode2)));
    }
    

    
}
