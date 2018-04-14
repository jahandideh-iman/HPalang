/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.HybridAutomataGeneration.SOSRules;

import HPalang.Core.ContinuousExpressions.ConstantContinuousExpression;
import HPalang.Core.ContinuousExpressions.DifferentialEquation;
import HPalang.Core.DiscreteExpressions.BinaryExpression;
import HPalang.Core.DiscreteExpressions.BinaryOperators.EqualityOperator;
import HPalang.Core.DiscreteExpressions.TrueConst;
import HPalang.Core.DiscreteExpressions.VariableExpression;
import HPalang.LTSGeneration.ExpressionScopeUnwrapper;
import HPalang.Core.ContinuousExpressions.Invarient;
import HPalang.Core.ModelCreationUtilities;
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
import HPalang.LTSGeneration.RunTimeStates.Event.Event;
import HPalang.LTSGeneration.RunTimeStates.GlobalRunTimeState;
import HPalang.LTSGeneration.RunTimeStates.PhysicalActorState;
import HPalang.LTSGeneration.StateInfo;
import HPalang.LTSGeneration.Transition;
import java.util.Collection;
import static HPalang.Core.ModelCreationUtilities.*;
import HPalang.Core.Variable;
import HPalang.LTSGeneration.RunTimeStates.ActorState;
import static HPalang.LTSGeneration.Utilities.QueryUtilities.IsDeadlock;

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
            location =  CreateInstantaneousLocation(globalStateInfo.State(),generator.CreateAUniqueLocationName(globalStateInfo.State()));
        
        else if (AllArePhysical(globalStateInfo.Outs()))
            location = CreatePhyscialLocationFrom(globalStateInfo.State(),generator.CreateAUniqueLocationName(globalStateInfo.State()));
        
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
    
    private Invarient EqualityInvarientFor(RealVariable var, float value)
    {
        return new Invarient(new BinaryExpression(
                new VariableExpression(urgVariable), 
                new EqualityOperator(),
                new ConstantContinuousExpression(value)));
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

    private Location CreateInstantaneousLocation(GlobalRunTimeState gs, String locName)
    {
        Location location = new Location(locName+"_I");

        if(IsDeadlock(gs))
            return location;
        
        AddConstantODEs(location, gs);
        
        for (RealVariable var : gs.EventsState().PoolState().Pool().AllVariables()) {
            location.AddEquation(new DifferentialEquation(var, Const(0f)));
        }
        
        location.AddEquation(new DifferentialEquation(urgVariable, Const(1f)));
        location.AddInvariant(new Invarient(CreateBinaryExpression(urgVariable,"==", Const(0))));

        return location;
    }



    private Location CreatePhyscialLocationFrom(GlobalRunTimeState gs, String locName)
    {
        Location location = new Location(locName+"_P");
        
        if(IsDeadlock(gs))
            return location;
        
        ExpressionScopeUnwrapper unwrapper = new ExpressionScopeUnwrapper();
        for(PhysicalActorState actorState : gs.ContinuousState().ActorStates())
        {
            String actorName = actorState.Actor().Name();
            Invarient convertedInvarient = 
                    (Invarient) unwrapper.Unwrap(actorState.Mode().GetInvarient(), actorName);
            
            location.AddInvariant(convertedInvarient);
            for(DifferentialEquation equation : actorState.Mode().GetEquations())
                location.AddEquation((DifferentialEquation) unwrapper.Unwrap(equation, actorName));
        }
        
        for(Event event : gs.EventsState().Events())
        {
            location.AddInvariant(CreateInvarient(event.Timer(), "<=", Const(event.Delay())));
            
            location.AddEquation(new DifferentialEquation(event.Timer(),Const(1f)));
        }
        
        for(RealVariable var: gs.EventsState().PoolState().Pool().AvailableVariables())
            location.AddEquation(new DifferentialEquation(var,Const(0f)));
        
        AddConstantODEs(location, gs);
        location.AddEquation(new DifferentialEquation(urgVariable, Const(0f)));
               
        
        return location;
    }
    
    private HybridLabel CreateInstantaneousLabel(Transition transition)
    {
        return CreateUrgentInstantaneousLabel(transition);
    }
    
    private HybridLabel CreateUrgentInstantaneousLabel(Transition transition)
    {
        Guard guard;
        
        if(transition.GetLabel().IsGuarded())
            guard = transition.GetLabel().Guard();
        else 
            guard =  new Guard(CreateBinaryExpression(urgVariable, "==", Const(0))); 
        return new HybridLabel(
                guard,
                transition.GetLabel().Resets(),
                false);
    }

    private HybridLabel CreateASAPInstantaneousLabel(Transition transition)
    {
        Guard guard;
        if(transition.GetLabel().IsGuarded())
            guard = transition.GetLabel().Guard();
        else 
            guard =  new Guard(new TrueConst()); 
        return new HybridLabel(
                guard,
                transition.GetLabel().Resets(),
                true);
    }
    

    private HybridLabel CreateGuardedLabelFrom(Transition transition)
    {
        return new HybridLabel(
                    transition.GetLabel().Guard(),
                    transition.GetLabel().Resets());
    }

    private void AddConstantODEs(Location location, GlobalRunTimeState gs)
    {
 
        ExpressionScopeUnwrapper unwrapper = new ExpressionScopeUnwrapper();
        for(ActorState actorState : gs.DiscreteState().ActorStates())
            for(Variable var : actorState.Actor().Type().Variables())
                if(var.Type() == Variable.Type.floatingPoint)
                    location.AddEquation(
                            new DifferentialEquation(
                                    unwrapper.Unwrap(var, actorState.Actor().Name()), 
                                    Const(0f)));
        
    }
}
