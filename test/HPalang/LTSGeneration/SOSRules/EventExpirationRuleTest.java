/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.SOSRules;

import Builders.StateInfoBuilder;
import HPalang.Core.ContinuousExpressions.ConstantContinuousExpression;
import HPalang.Core.ContinuousVariable;
import HPalang.Core.SimpleContinuousVariablePool;
import HPalang.Core.Variables.RealVariable;
import HPalang.LTSGeneration.Labels.ContinuousLabel;
import HPalang.LTSGeneration.Labels.NetworkLabel;
import HPalang.LTSGeneration.Labels.Reset;
import HPalang.LTSGeneration.Labels.SoftwareLabel;
import HPalang.LTSGeneration.RunTimeStates.Event.Action;
import HPalang.LTSGeneration.RunTimeStates.Event.Event;
import HPalang.LTSGeneration.RunTimeStates.EventsState;

import HPalang.LTSGeneration.RunTimeStates.GlobalRunTimeState;
import HPalang.LTSGeneration.RunTimeStates.VariablePoolState;
import HPalang.LTSGeneration.SOSRule;
import HPalang.LTSGeneration.StateInfo;
import HPalang.LTSGeneration.Transition;
import Mocks.ActionMonitor;
import Mocks.TransitionCollectorChecker;
import static TestUtilities.CoreUtility.SimpleStateInfo;
import java.util.Collections;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 *
 * @author Iman Jahandideh
 */
public class EventExpirationRuleTest extends SOSRuleTestFixture
{
    @Before
    public void Setup()
    {
        rule = new EventExpirationRule();
    }
    
    @Test
    public void IfThereIsNoSoftwareAndNetworkTransitionExpiratesEvents()
    {
        Event event = CreateEvent(new ActionMonitor());
        EventsState eventsState = CreateEventStateWith(event);
        globalState.AddSubstate(eventsState);
        
        GlobalRunTimeState nextGlobalState = globalState.DeepCopy();
        nextGlobalState.EventsState().PoolState().Pool().Release(event.Timer());
        nextGlobalState.EventsState().RemoveEvent(event);
        
        rule.TryApply(SimpleStateInfo(globalState), transitionCollectorChecker);
        
        transitionCollectorChecker.ExpectTransition(EventTransitionLabel(event), nextGlobalState);
        assertTrue(((ActionMonitor)event.Action()).isExecuted);
    }
    
    @Test
    public void DoesNotExpiresEventsIfThereIsSoftwareAction()
    {
        Event event = CreateEvent(new ActionMonitor());
        EventsState eventsState = CreateEventStateWith(event);
        globalState.AddSubstate(eventsState);
        
        StateInfo stateInfoWithSoftwareTransition = new StateInfoBuilder().
                WithState(globalState).
                AddOutTransition(new Transition(globalState, new SoftwareLabel(), globalState)).
                Build();
           

        
        rule.TryApply(stateInfoWithSoftwareTransition, transitionCollectorChecker);
        
        transitionCollectorChecker.ExpectNoTransition();
        assertFalse(((ActionMonitor)event.Action()).isExecuted);
    }
    
    @Test
    public void DoesNotExpiresEventsIfThereIsNetworkAction()
    {
        Event event = CreateEvent(new ActionMonitor());
        EventsState eventsState = CreateEventStateWith(event);
        globalState.AddSubstate(eventsState);
        
        StateInfo stateInfoWithSoftwareTransition = new StateInfoBuilder().
                WithState(globalState).
                AddOutTransition(new Transition(globalState, new NetworkLabel(), globalState)).
                Build();
           
        
        
        rule.TryApply(stateInfoWithSoftwareTransition, transitionCollectorChecker);
        
        transitionCollectorChecker.ExpectNoTransition();
        assertFalse(((ActionMonitor)event.Action()).isExecuted);
    }
    
    private ContinuousLabel EventTransitionLabel(Event event)
    {
        String guard = event.Timer().Name()+"=="+event.Delay();
        Reset reset = new Reset(event.Timer(), new ConstantContinuousExpression(0));
        return new ContinuousLabel(guard, Reset.From(reset));
    }
    
    private Event CreateEvent(Action action)
    {
        return new Event(5, new RealVariable("timer"), action);
    }
    
    public EventsState CreateEventStateWith(Event event)
    {
        VariablePoolState poolState = new VariablePoolState(new SimpleContinuousVariablePool(0));
        EventsState eventsState = new EventsState();
        eventsState.AddEvent(event);
        eventsState.SetPool(poolState);
        return eventsState;
    }
}
