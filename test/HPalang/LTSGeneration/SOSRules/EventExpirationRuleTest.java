/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.SOSRules;

import Builders.StateInfoBuilder;
import HPalang.Core.ContinuousExpressions.ConstantContinuousExpression;
import HPalang.Core.CreationUtilities;
import HPalang.Core.DiscreteExpressions.BinaryExpression;
import HPalang.Core.DiscreteExpressions.BinaryOperators.EqualityOperator;
import HPalang.Core.DiscreteExpressions.VariableExpression;
import HPalang.LTSGeneration.Labels.*;
import HPalang.LTSGeneration.RunTimeStates.Event.Event;
import HPalang.LTSGeneration.RunTimeStates.GlobalRunTimeState;
import HPalang.LTSGeneration.StateInfo;
import HPalang.LTSGeneration.Transition;
import static HPalang.LTSGeneration.Utilities.CreationUtility.CreateDeadlockState;
import static HPalang.LTSGeneration.Utilities.CreationUtility.CreateDeadlockTransition;
import Mocks.ActionMonitor;
import static TestUtilities.CoreUtility.*;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 *
 * @author Iman Jahandideh
 */
public class EventExpirationRuleTest extends SOSRuleTestFixture
{
    float arbiraryDelay = 1f;
    ActionMonitor actionMonitor;
    @Before
    public void Setup()
    {
        rule = new EventExpirationRule();
        actionMonitor = new ActionMonitor();
    }
    
    @Test
    public void IfThereIsNoSoftwareAndNetworkTransitionExpiratesEvents()
    {
        ResetEventStatePool(globalState);
        Event event = globalState.EventsState().RegisterEvent(arbiraryDelay, actionMonitor);
        
        GlobalRunTimeState nextGlobalState = globalState.DeepCopy();
        nextGlobalState.EventsState().UnregisterEvent(event);
        
        ApplyRuleOn(globalState);
        //rule.TryApply(SimpleStateInfo(globalState), transitionCollectorChecker);
        
        transitionCollectorChecker.ExpectTransition(EventTransitionLabel(event), nextGlobalState);
        assertTrue(((ActionMonitor)event.Action()).isExecuted);
        VerifyEqualOutputForMultipleApply(SimpleStateInfo(globalState));
    }
    
    @Test
    public void GoesToDeadlockForADeadlockedEvent()
    {
        ResetEventStatePool(globalState);
        
        actionMonitor.SetDeadlocked(true);
        Event event = globalState.EventsState().RegisterEvent(arbiraryDelay, actionMonitor);
        
        ApplyRuleOn(globalState);

        transitionCollectorChecker.ExpectTransition(CreateDeadlockTransition(), CreateDeadlockState());
        assertFalse(actionMonitor.isExecuted);
        VerifyEqualOutputForMultipleApply(SimpleStateInfo(globalState));
    }
    
    @Test
    public void DoesNotExpiresEventsIfThereIsSoftwareAction()
    {
        ResetEventStatePool(globalState);
        Event event = globalState.EventsState().RegisterEvent(arbiraryDelay, actionMonitor);
        
        StateInfo stateInfoWithSoftwareTransition = new StateInfoBuilder().
                WithState(globalState).
                AddOutTransition(new Transition(globalState, new SoftwareLabel(), globalState)).
                Build();
           

        ApplyAndVerifyRuleOn(stateInfoWithSoftwareTransition);
        //rule.TryApply(stateInfoWithSoftwareTransition, transitionCollectorChecker);
        
        transitionCollectorChecker.ExpectNoTransition();
        assertFalse(((ActionMonitor)event.Action()).isExecuted);
        VerifyEqualOutputForMultipleApply(stateInfoWithSoftwareTransition);
    }
    
    @Test
    public void DoesNotExpireEventsIfThereIsNetworkAction()
    {
        ResetEventStatePool(globalState);
        Event event = globalState.EventsState().RegisterEvent(arbiraryDelay, actionMonitor);
        
        StateInfo stateInfoWithNetworkTransition = new StateInfoBuilder().
                WithState(globalState).
                AddOutTransition(new Transition(globalState, new NetworkLabel(), globalState)).
                Build();
        
        ApplyAndVerifyRuleOn(stateInfoWithNetworkTransition);
        //rule.TryApply(stateInfoWithNetworkTransition, transitionCollectorChecker);
        
        transitionCollectorChecker.ExpectNoTransition();
        assertFalse(((ActionMonitor)event.Action()).isExecuted);
        VerifyEqualOutputForMultipleApply(stateInfoWithNetworkTransition);
    }
    
    private ContinuousLabel EventTransitionLabel(Event event)
    {
        Guard guard = new Guard(new BinaryExpression(
                new VariableExpression(event.Timer()), 
                new EqualityOperator(), 
                new ConstantContinuousExpression(event.Delay())));
        
        Reset reset = new Reset(event.Timer(), new ConstantContinuousExpression(0));
        return new ContinuousLabel(guard, Reset.From(reset));
    }
}
