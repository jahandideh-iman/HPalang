/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.HybridAutomataGeneration.SOSRules;

import HPalang.Core.DifferentialEquation;
import HPalang.Core.DiscreteExpressions.TrueConst;
import HPalang.Core.Variables.RealVariable;
import HPalang.HybridAutomataGeneration.HybridAutomatonGenerator;
import HPalang.HybridAutomataGeneration.HybridLabel;
import HPalang.HybridAutomataGeneration.Location;
import HPalang.HybridAutomataGeneration.SOSRule;
import HPalang.LTSGeneration.Label;
import HPalang.LTSGeneration.Labels.ContinuousLabel;
import HPalang.LTSGeneration.Labels.Guard;
import HPalang.LTSGeneration.Labels.NetworkLabel;
import HPalang.LTSGeneration.Labels.SoftwareLabel;
import HPalang.LTSGeneration.RunTimeStates.GlobalRunTimeState;
import HPalang.LTSGeneration.RunTimeStates.PhysicalActorState;
import HPalang.LTSGeneration.StateInfo;
import HPalang.LTSGeneration.Transition;
import java.util.Collection;

/**
 *
 * @author Iman Jahandideh
 */
public class ConversionRule implements SOSRule
{

    private static final RealVariable urgVariable = new RealVariable("urg");
    
    @Override
    public void TryApply(StateInfo globalStateInfo, HybridAutomatonGenerator generator)
    {
        Location location;
        
        if(AllAreSoftware(globalStateInfo.Outs()) || AllIsNetwork(globalStateInfo.Outs()))
            location =  CreateInstantaneousLocation();
        
        else if (AllArePhysical(globalStateInfo.Outs()))
            location = CreatePhyscialLocationFrom(globalStateInfo.State());
        
        else 
            throw new RuntimeException("State info is invalid");
        
        generator.AddLocationFor(location, globalStateInfo.State());
    }

    @Override
    public void TryApply(Transition transition, HybridAutomatonGenerator generator)
    {
        Location origin = generator.LocationOf(transition.GetOrign());
        Location destination = generator.LocationOf(transition.GetDestination());
      
        HybridLabel label;
        
        Label transitionLabel = transition.GetLabel();
        if(transitionLabel instanceof SoftwareLabel || transitionLabel instanceof NetworkLabel)
            label = CreateInstantaneousLabel(transition);
        
        else if(transitionLabel instanceof ContinuousLabel)
            label = CreateGuardedLabelFrom(transition);
        else
            throw new RuntimeException("Unknow Label");
        
        generator.AddTransition(origin, label, destination);
    }
    
    private String EqualityInvarientFor(RealVariable var, float value)
    {
        return String.format("%s == %s", var.Name(), value);
    }

    // TODO: Merge these to a signle generic method.
    private boolean AllAreSoftware(Collection<Transition> trans)
    {
        for(Transition tran : trans)
            if(tran.GetLabel() instanceof SoftwareLabel == false)
                return false;
        return true;
    }

    private boolean AllIsNetwork(Collection<Transition> trans)
    {
        for (Transition tran : trans) {
            if (tran.GetLabel() instanceof NetworkLabel == false) {
                return false;
            }
        }
        return true;

    }
    
    private boolean AllArePhysical(Collection<Transition> trans)
    {
        for(Transition tran : trans)
            if(tran.GetLabel() instanceof ContinuousLabel == false)
                return false;
        return true;
    }

    private Location CreateInstantaneousLocation()
    {
        Location location = new Location();

        location.AddEquation(new DifferentialEquation(urgVariable, "1"));
        location.AddInvariant(EqualityInvarientFor(urgVariable, 0));
        
        return location;
    }



    private Location CreatePhyscialLocationFrom(GlobalRunTimeState state)
    {
        Location location = new Location();
        
        for(PhysicalActorState actorState : state.ContinuousState().ActorStates())
        {
            
            location.AddInvariant(actorState.Mode().GetInvarient());
            location.AddEquations(actorState.Mode().GetEquations());
        }
        
        return location;
    }

    private HybridLabel CreateInstantaneousLabel(Transition transition)
    {
        return new HybridLabel(
                new Guard(new TrueConst()),
                transition.GetLabel().Resets(),
                true);
    }

    private HybridLabel CreateGuardedLabelFrom(Transition transition)
    {
        return new HybridLabel(
                    transition.GetLabel().Guard(),
                    transition.GetLabel().Resets());
    }
}
