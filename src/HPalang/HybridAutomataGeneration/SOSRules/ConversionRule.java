/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.HybridAutomataGeneration.SOSRules;

import HPalang.HybridAutomataGeneration.HybridAutomatonGenerator;
import HPalang.HybridAutomataGeneration.Location;
import HPalang.LTSGeneration.ConditionalLabel;
import HPalang.LTSGeneration.Label;
import HPalang.LTSGeneration.RunTimeStates.ActorRunTimeState;
import HPalang.LTSGeneration.RunTimeStates.ContinuousBehavior;
import HPalang.LTSGeneration.RunTimeStates.GlobalRunTimeState;
import HPalang.LTSGeneration.TauLabel;
import HPalang.LTSGeneration.Transition;

/**
 *
 * @author Iman Jahandideh
 */
public class ConversionRule implements TransitionSOSRule
{

    @Override
    public void TryApply(Transition transition, HybridAutomatonGenerator generator)
    {
        Location origin = generator.ToLocation(transition.GetOrign());
        Location destination = generator.ToLocation(transition.GetDestination());
        
        ConditionalLabel label = null;
        if(transition.GetLabel() instanceof TauLabel)
            label = new ConditionalLabel("true");
        else
            label = new ConditionalLabel(((ConditionalLabel)transition.GetLabel()).GetCondition());
        
        generator.AddTransition(origin, label, destination);
    }
    
}
