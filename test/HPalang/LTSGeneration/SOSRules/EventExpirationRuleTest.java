/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.SOSRules;

import Builders.StateInfoBuilder;
import HPalang.Core.ContinuousExpressions.ConstantContinuousExpression;
import HPalang.LTSGeneration.Labels.*;
import HPalang.LTSGeneration.RunTimeStates.Event.Event;
import HPalang.LTSGeneration.RunTimeStates.GlobalRunTimeState;
import HPalang.LTSGeneration.StateInfo;
import HPalang.LTSGeneration.Transition;
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
    @Before
    public void Setup()
    {
        rule = new EventExpirationRule();
    }
    
    @Test
    public void IfThereIsNoSoftwareAndNetworkTransitionExpiratesEvents()
    {
        ResetEventStatePool(globalState);
        Event event = globalState.EventsState().RegisterEvent(arbiraryDelay, new ActionMonitor());
        
        GlobalRunTimeState nextGlobalState = globalState.DeepCopy();
        nextGlobalState.EventsState().UnregisterEvent(event);
        
        ApplyAndVerifyRuleOn(globalState);
        //rule.TryApply(SimpleStateInfo(globalState), transitionCollectorChecker);
        
        transitionCollectorChecker.ExpectTransition(EventTransitionLabel(event), nextGlobalState);
        assertTrue(((ActionMonitor)event.Action()).isExecuted);
        VerifyEqualOutputForMultipleApply(SimpleStateInfo(globalState));
    }
    
    @Test
    public void DoesNotExpiresEventsIfThereIsSoftwareAction()
    {
        ResetEventStatePool(globalState);
        Event event = globalState.EventsState().RegisterEvent(arbiraryDelay, new ActionMonitor());
        
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
    public void DoesNotExpiresEventsIfThereIsNetworkAction()
    {
        ResetEventStatePool(globalState);
        Event event = globalState.EventsState().RegisterEvent(arbiraryDelay, new ActionMonitor());
        
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
        String guard = event.Timer().Name()+"=="+event.Delay();
        Reset reset = new Reset(event.Timer(), new ConstantContinuousExpression(0));
        return new ContinuousLabel(guard, Reset.From(reset));
    }
}
