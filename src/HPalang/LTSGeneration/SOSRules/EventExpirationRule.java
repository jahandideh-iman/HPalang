/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.SOSRules;

import HPalang.Core.ContinuousExpressions.ConstantContinuousExpression;
import HPalang.Core.DiscreteExpressions.BinaryExpression;
import HPalang.Core.DiscreteExpressions.BinaryOperators.EqualityOperator;
import HPalang.Core.DiscreteExpressions.VariableExpression;
import HPalang.LTSGeneration.LTSGenerator;
import HPalang.LTSGeneration.Labels.ContinuousLabel;
import HPalang.LTSGeneration.Labels.Guard;
import HPalang.LTSGeneration.Labels.Reset;
import HPalang.LTSGeneration.RunTimeStates.Event.Event;
import HPalang.LTSGeneration.RunTimeStates.EventsState;
import HPalang.LTSGeneration.RunTimeStates.GlobalRunTimeState;
import HPalang.LTSGeneration.SOSRule;
import static HPalang.LTSGeneration.SOSRules.Utilities.HasNetworkActions;
import static HPalang.LTSGeneration.SOSRules.Utilities.HasSoftwareActions;
import static HPalang.LTSGeneration.SOSRules.Utilities.NoSoftwareActions;
import HPalang.LTSGeneration.StateInfo;
import HPalang.LTSGeneration.TransitionCollector;
import HPalang.LTSGeneration.Utilities.CreationUtility;
import java.util.Collection;

/**
 *
 * @author Iman Jahandideh
 */
public class EventExpirationRule implements SOSRule
{

    @Override
    public void TryApply(StateInfo globalStateInfo, TransitionCollector generator)
    {
        if(HasSoftwareActions(globalStateInfo.Outs()) || HasNetworkActions(globalStateInfo.Outs()))
            return;
        
        Collection<Event> events = globalStateInfo.State().EventsState().Events();
        
        for(Event event : events)
        {
            if(event.Action().IsDeadlock(globalStateInfo.State()))
            {
                generator.AddTransition(CreationUtility.CreateDeadlockTransition(), CreationUtility.CreateDeadlockState());
            }
            else
            {
                GlobalRunTimeState newGlobalState = globalStateInfo.State().DeepCopy();
                EventsState newEventsState = newGlobalState.EventsState();

                newEventsState.UnregisterEvent(event);
                event.Action().Execute(newGlobalState);

                generator.AddTransition(CreateEventTransitionLabel(event), newGlobalState); 
            }
        }
    }
    
    public ContinuousLabel CreateEventTransitionLabel(Event event)
    {
        Guard guard = new Guard(new BinaryExpression(
                new VariableExpression(event.Timer()), 
                new EqualityOperator(), 
                new ConstantContinuousExpression(event.Delay())));
        
        Reset reset = new Reset(event.Timer(), new ConstantContinuousExpression(0));
        return new ContinuousLabel(guard, Reset.From(reset));
    }
}
