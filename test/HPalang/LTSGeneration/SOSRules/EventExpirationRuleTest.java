/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.SOSRules;

import HPalang.Core.ContinuousExpressions.ConstantContinuousExpression;
import HPalang.Core.ContinuousVariable;
import HPalang.Core.ContinuousVariablePool;
import HPalang.LTSGeneration.Labels.ContinuousLabel;
import HPalang.LTSGeneration.Labels.Reset;
import HPalang.LTSGeneration.RunTimeStates.Event.Action;
import HPalang.LTSGeneration.RunTimeStates.Event.Event;
import HPalang.LTSGeneration.RunTimeStates.EventsState;

import HPalang.LTSGeneration.RunTimeStates.GlobalRunTimeState;
import HPalang.LTSGeneration.RunTimeStates.VariablePoolState;
import Mocks.ActionMonitor;
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
        ltsGenerator.AddSOSRule(new EventExpirationRule());
    }
    
    @Test
    public void IfThereIsNoSoftwareAndNetworkTransitionExpiratesEvents()
    {
        ActionMonitor actionMonitor = new ActionMonitor();
        Event event = CreateEvent(actionMonitor);

        VariablePoolState poolState = new VariablePoolState(new ContinuousVariablePool(0));
        EventsState eventsState = new EventsState();
        eventsState.AddEvent(event);
        eventsState.SetPool(poolState);
        
        globalState = new GlobalRunTimeState();
        globalState.AddSubstate(eventsState);
        
        generatedLTS = ltsGenerator.Generate(globalState);
        
        GlobalRunTimeState nextGlobalState = globalState.DeepCopy();
        EventsState nextEventsState = nextGlobalState.EventsState();
        nextEventsState.PoolState().Pool().Release(event.Timer());
        nextEventsState.RemoveEvent(event);

        assertTrue(generatedLTS.HasTransition(globalState, EventTransitionLabel(event), nextGlobalState));
        assertTrue(((ActionMonitor)event.Action()).isExecuted);
    }
    
    private ContinuousLabel EventTransitionLabel(Event event)
    {
        String guard = event.Timer().Name()+"=="+event.Delay();
        Reset reset = new Reset(event.Timer(), new ConstantContinuousExpression(0));
        return new ContinuousLabel(guard, Reset.ResetsFrom(reset));
    }
    
    private Event CreateEvent(Action action)
    {
        return new Event(5, new ContinuousVariable("timer"), action);
    }
    
}
