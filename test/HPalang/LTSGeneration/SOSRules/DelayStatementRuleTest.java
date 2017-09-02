/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.SOSRules;

import HPalang.Core.Statements.DelayStatement;
import HPalang.LTSGeneration.Labels.SoftwareLabel;
import HPalang.LTSGeneration.RunTimeStates.ActorState;
import HPalang.LTSGeneration.RunTimeStates.Event.Event;
import HPalang.LTSGeneration.RunTimeStates.Event.ResumeSoftwareActorAction;
import HPalang.LTSGeneration.RunTimeStates.GlobalRunTimeState;
import HPalang.LTSGeneration.RunTimeStates.SoftwareActorState;
import org.junit.Test;
import org.junit.Before;
import static TestUtilities.CoreUtility.*;
import static TestUtilities.NetworkingUtility.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;


/**
 *
 * @author Iman Jahandideh
 */

public class DelayStatementRuleTest extends SOSRuleTestFixture
{ 
    @Before
    public void Setup()
    {
        rule = new DelayStatementRule();
    }
    
    @Test
    public void SuspendsTheActorAndAddsTheDelayToEventsState()
    {
        SoftwareActorState actorState = CreateSoftwareActorState("actor");
        
        float delayDuration = 3;
        DelayStatement delayStatement = new DelayStatement(delayDuration);
        
        EnqueueStatement(delayStatement, actorState);
        AddActorState(actorState, globalState);
        ResetEventStatePool(globalState);
        
        rule.TryApply(SimpleStateInfo(globalState), transitionCollectorChecker);
        
        GlobalRunTimeState expectedGlobalState = globalState.DeepCopy();
        ClearStatementsFor(actorState.SActor(), expectedGlobalState);
        RegisterEvent(delayDuration, DelayAction(actorState), expectedGlobalState);
        FindActorState(actorState.SActor(), expectedGlobalState).SetSuspended(true);
        
        transitionCollectorChecker.ExpectTransition(new SoftwareLabel(), expectedGlobalState);
    }
    
    private ResumeSoftwareActorAction DelayAction(SoftwareActorState actorState)
    {
        return new ResumeSoftwareActorAction(actorState.SActor());
    }
    
}
