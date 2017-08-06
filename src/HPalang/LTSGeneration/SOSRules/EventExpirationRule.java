/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.SOSRules;

import HPalang.Core.ContinuousExpressions.ConstantContinuousExpression;
import HPalang.LTSGeneration.LTSGenerator;
import HPalang.LTSGeneration.Labels.ContinuousLabel;
import HPalang.LTSGeneration.Labels.Reset;
import HPalang.LTSGeneration.RunTimeStates.Event.Event;
import HPalang.LTSGeneration.RunTimeStates.EventsState;
import HPalang.LTSGeneration.RunTimeStates.GlobalRunTimeState;
import HPalang.LTSGeneration.SOSRule;
import HPalang.LTSGeneration.StateInfo;
import java.util.Collection;

/**
 *
 * @author Iman Jahandideh
 */
public class EventExpirationRule implements SOSRule
{

    @Override
    public void TryApply(StateInfo globalStateInfo, LTSGenerator generator)
    {

        Collection<Event> events = globalStateInfo.State().EventsState().Events();
        
        for(Event event : events)
        {
            GlobalRunTimeState newGlobalState = globalStateInfo.State().DeepCopy();
            EventsState newEventsState = newGlobalState.EventsState();
            
            newEventsState.RemoveEvent(event);
            newEventsState.PoolState().Pool().Release(event.Timer());
            event.Action().Execute(newGlobalState);
            
            generator.AddTransition(CreateEventTransitionLabel(event), newGlobalState); 
        }
    }
    
    public ContinuousLabel CreateEventTransitionLabel(Event event)
    {
        String guard = event.Timer().Name()+"=="+event.Delay();
        Reset reset = new Reset(event.Timer(), new ConstantContinuousExpression(0));
        return new ContinuousLabel(guard, Reset.ResetsFrom(reset));
    }
}
