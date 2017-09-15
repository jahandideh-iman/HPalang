/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.SOSRules;

import HPalang.Core.Mode;
import HPalang.Core.PhysicalActor;
import HPalang.Core.Statement;
import HPalang.Core.Statements.ModeChangeStatement;
import HPalang.LTSGeneration.Labels.SoftwareLabel;
import HPalang.LTSGeneration.RunTimeStates.GlobalRunTimeState;
import HPalang.LTSGeneration.RunTimeStates.PhysicalActorState;
import org.junit.Test;
import static org.junit.Assert.*;
import static TestUtilities.CoreUtility.*;
import org.junit.Before;

/**
 *
 * @author Iman Jahandideh
 */
public class ModeChangeStatementRuleTest extends SOSRuleTestFixture
{
    @Before
    public void Setup()
    {
        rule = new ModeChangeStatementRule();
    }
    
    @Test
    public void ChangesTheModeToTheSpecifiedMode()
    {
        Mode mode1 = CreateEmptyMode("mode1");
        Mode mode2 = CreateEmptyMode("Mode2");
        PhysicalActorState actorState = CreatePhysicalActorState("pActor", mode1, mode2);
        
        Statement modeChangeStatement = new ModeChangeStatement(mode2);
        
        EnqueueStatement(modeChangeStatement, actorState);
        AddActorState(actorState, globalState);
        
        
        ApplyAndVerifyRuleOn(globalState);
        //rule.TryApply(SimpleStateInfo(globalState), transitionCollectorChecker);
        
        GlobalRunTimeState expectedGlobalState = globalState.DeepCopy();
        ClearStatementsFor(actorState.PActor(), expectedGlobalState);
        SetMode(mode2, actorState.PActor(), expectedGlobalState);
        
        transitionCollectorChecker.ExpectTransition(new SoftwareLabel(), expectedGlobalState);
    }

}
