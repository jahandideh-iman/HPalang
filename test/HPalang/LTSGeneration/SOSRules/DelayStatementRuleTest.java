/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.SOSRules;

import HPalang.Core.ContinuousExpressions.ConstantContinuousExpression;
import HPalang.Core.Statements.DelayStatement;
import HPalang.LTSGeneration.Labels.Reset;
import HPalang.LTSGeneration.Labels.SoftwareLabel;
import HPalang.LTSGeneration.RunTimeStates.ActorState;
import HPalang.LTSGeneration.RunTimeStates.Event.Event;
import HPalang.LTSGeneration.RunTimeStates.Event.ResumeSoftwareActorAction;
import HPalang.LTSGeneration.RunTimeStates.GlobalRunTimeState;
import HPalang.LTSGeneration.RunTimeStates.SoftwareActorState;
import HPalang.LTSGeneration.Utilities.CreationUtility;
import static HPalang.LTSGeneration.Utilities.CreationUtility.CreateDeadlockState;
import static HPalang.LTSGeneration.Utilities.CreationUtility.CreateDeadlockTransition;
import org.junit.Test;
import org.junit.Before;
import static TestUtilities.CoreUtility.*;
import static TestUtilities.NetworkingUtility.*;
import java.util.Set;
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
        ResetEventStatePool(globalState,1);
        
        ApplyRuleOn(globalState);
        
        GlobalRunTimeState expectedGlobalState = globalState.DeepCopy();
        ClearStatementsFor(actorState.SActor(), expectedGlobalState);
        RegisterEvent(delayDuration, DelayAction(actorState), expectedGlobalState);
        FindActorState(actorState.SActor(), expectedGlobalState).SetSuspended(true);
        
        transitionCollectorChecker.ExpectTransition(new SoftwareLabel(ResetsForEvent(delayStatement, globalState)), expectedGlobalState);
        VerifyEqualOutputForMultipleApply(SimpleStateInfo(globalState));
    }
    
    @Test
    public void GoesToDeadlockWhenNoRealVariableIsAvailable()
    {
        SoftwareActorState actorState = CreateSoftwareActorState("actor");
        
        float delayDuration = 3;
        DelayStatement delayStatement = new DelayStatement(delayDuration);
        
        EnqueueStatement(delayStatement, actorState);
        AddActorState(actorState, globalState);
        ResetEventStatePool(globalState, 0);
        
        ApplyRuleOn(globalState);
        

       
        transitionCollectorChecker.ExpectTransition(CreateDeadlockTransition(), CreateDeadlockState());
        VerifyEqualOutputForMultipleApply(SimpleStateInfo(globalState));
    }
    
    @Test
    public void IsNotAppliedWhenActorIsSuspended()
    {
        SoftwareActorState actorState = CreateSoftwareActorState("actor");
        actorState.SetSuspended(true);
        
        float delayDuration = 3;
        DelayStatement delayStatement = new DelayStatement(delayDuration);
        
        EnqueueStatement(delayStatement, actorState);
        AddActorState(actorState, globalState);
        ResetEventStatePool(globalState);
        
        ApplyRuleOn(globalState);
        
        transitionCollectorChecker.ExpectNoTransition();
        VerifyEqualOutputForMultipleApply(SimpleStateInfo(globalState));
    }
    
    private ResumeSoftwareActorAction DelayAction(SoftwareActorState actorState)
    {
        return new ResumeSoftwareActorAction(actorState.SActor());
    }
    
    private Set<Reset> ResetsForEvent(DelayStatement statement,GlobalRunTimeState gs)
    {
        Event event =  gs.DeepCopy().EventsState().RegisterEvent(statement.GetDelay(),null);
        
        Reset reset = new Reset(event.Timer(), new ConstantContinuousExpression(0));
        return Reset.From(reset);
    }
    
}
