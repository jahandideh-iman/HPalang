/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Convertors;

import HPalang.Core.ContinuousExpressions.DifferentialEquation;
import HPalang.HybridAutomataGeneration.HybridAutomaton;
import HPalang.SpaceEx.Convertor.SpaceExToXMLConvertor;
import HPalang.SpaceEx.Core.BaseComponent;
import HPalang.SpaceEx.Core.Flow;
import HPalang.SpaceEx.Core.HybridLabel;
import HPalang.Core.ContinuousExpressions.Invarient;
import HPalang.Core.DiscreteExpressions.BinaryExpression;
import HPalang.Core.DiscreteExpressions.BinaryOperators.LogicalOrOperator;
import HPalang.Core.Expression;
import static HPalang.Core.ModelCreationUtilities.CreateInvarient;
import HPalang.Core.TransitionSystem.Transition;
import HPalang.Core.Variables.RealVariable;
import HPalang.LTSGeneration.Labels.Guard;
import HPalang.LTSGeneration.Labels.Reset;
import HPalang.SpaceEx.Convertor.SXExpressionFixer;
import HPalang.SpaceEx.Core.Component;
import HPalang.SpaceEx.Core.ComponentInstance;
import HPalang.SpaceEx.Core.HybridTransition;
import HPalang.SpaceEx.Core.Location;
import HPalang.SpaceEx.Core.NetworkComponent;
import HPalang.SpaceEx.Core.RealParameter;
import HPalang.SpaceEx.Core.SpaceExModel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Iman Jahandideh
 */
public class HybridAutomatonToSXConvertor
{
    
    private SXExpressionFixer sxExpressionFixer = new SXExpressionFixer();
    
    
    
    public String Convert(HybridAutomaton hybridAutomaton)
    {
        SpaceExModel spaceExModel = ConvertHAToSpaceExModel(hybridAutomaton);
        
        return new SpaceExToXMLConvertor().Convert(spaceExModel);
    }
    
    private SpaceExModel ConvertHAToSpaceExModel(HybridAutomaton automaton)
    {
        SpaceExModel spaceExModel = new SpaceExModel();
        
        Component model = CreateModelComponent(automaton);
        Component timer = CreateTimerComponent();
        Component system = CreateSystemComponent(timer,model);
        
        spaceExModel.AddComponent(model);
        spaceExModel.AddComponent(timer);
        
        spaceExModel.AddComponent(system);
        
        return spaceExModel;
    }

    private BaseComponent CreateModelComponent(HybridAutomaton automaton)
    {
        BaseComponent model = new BaseComponent("Model");
        Map<HPalang.HybridAutomataGeneration.Location, HPalang.SpaceEx.Core.Location> locationsMap = new HashMap<>();
        int i = 0;
        
        for(HPalang.HybridAutomataGeneration.Location hybridLocation : automaton.GetLocations())
        {
            HPalang.SpaceEx.Core.Location spaceExLocation = 
                    new HPalang.SpaceEx.Core.Location(hybridLocation.Name());
            
            for(Invarient invarient : hybridLocation.GetInvarients())
                spaceExLocation.AddInvarient(invarient);
            
            for(DifferentialEquation ode : hybridLocation.GetEquations())
                spaceExLocation.AddFlow(new Flow(ode));
            
            model.AddLocation(spaceExLocation);
            i++;
            
            locationsMap.put(hybridLocation, spaceExLocation);
            
            if(automaton.InitialState().InnerState() == hybridLocation)
                model.SetInitialLocation(spaceExLocation);
        }
        for(Transition<HPalang.HybridAutomataGeneration.Location> hybridTransition : automaton.Transitions())
        {
            
            //------------------------------ AD HOCK SOLUTION FOR Logical OR------------------------------
            Expression dnfGuardExpr = new DisjunctiveNormalFormConvertor().Convert(hybridTransition.GetLabel().Guard().Expression());
            if (IsDisjunction(dnfGuardExpr)) 
            {
                Expression partialExpr = dnfGuardExpr;
                while (IsDisjunction(partialExpr)) 
                {
                    BinaryExpression bPartialExpr = (BinaryExpression)partialExpr;
                    
                    HPalang.SpaceEx.Core.HybridLabel spaceExLabel = new HybridLabel();
                    Guard guard = new Guard(bPartialExpr.Operand1());
                    spaceExLabel.AddGuard(sxExpressionFixer.Convert(guard));
                    hybridTransition.GetLabel().Resets().forEach((reset) -> spaceExLabel.AddAssignment(sxExpressionFixer.Convert((Reset) reset)));

                    model.AddTransition(
                            new HybridTransition(
                                    locationsMap.get(hybridTransition.GetOrign().InnerState()),
                                    spaceExLabel,
                                    locationsMap.get(hybridTransition.GetDestination().InnerState()),
                                    false
                            ));
                    
                    partialExpr = bPartialExpr.Operand2();
                }
                continue;
            }
            //------------------------------------------------------------------------------
            HPalang.HybridAutomataGeneration.HybridLabel hybridLabel = (HPalang.HybridAutomataGeneration.HybridLabel)hybridTransition.GetLabel();
            
            HPalang.SpaceEx.Core.HybridLabel spaceExLabel = new HybridLabel();
            
            spaceExLabel.AddGuard(sxExpressionFixer.Convert(hybridLabel.Guard()));
            
            hybridLabel.Resets().forEach((reset) -> spaceExLabel.AddAssignment(sxExpressionFixer.Convert(reset)));
            
            
            model.AddTransition(
                    new HybridTransition(
                            locationsMap.get(hybridTransition.GetOrign().InnerState()),
                            spaceExLabel,
                            locationsMap.get(hybridTransition.GetDestination().InnerState()),
                            hybridLabel.IsASAP()
                    ));
       
        }
        

        
        for(String variable : automaton.Variables())
            model.AddParameter(new RealParameter(variable, true));
        return model;
    }

    private Component CreateTimerComponent()
    {
        BaseComponent timerComp = new BaseComponent("Timer");
        
        timerComp.AddParameter(new RealParameter("duration", false));       
        timerComp.AddParameter(new RealParameter("time", false));
        
        Location loc1 = new Location("loc1");
        Location loc2 = new Location("loc2");
        
        loc1.AddInvarient(CreateInvarient(new RealVariable("time"), "<=", new RealVariable("duration")));
        loc1.AddFlow(new Flow("time' == 1"));
        loc1.AddFlow(new Flow("duration' == 0"));
        
        loc2.AddFlow(new Flow("time' == 0"));
        loc2.AddFlow(new Flow("duration' == 0"));
        
        timerComp.AddTransition(loc1, new HybridLabel().AddGuard("time == duration"), loc2);

        timerComp.SetInitialLocation(loc1);
        return timerComp;
    }
    

    private Component CreateSystemComponent(Component timer, Component model)
    {
        NetworkComponent system = new NetworkComponent("System");
        ComponentInstance timerInst = new ComponentInstance("timer", timer);
        system.AddParameter(new RealParameter("time", false));
        system.AddParameter(new RealParameter("duration", false));
        timerInst.SetBinding("time", "time");
        timerInst.SetBinding("duration", "duration");
        system.AddInstance(timerInst);
        
        system.AddInstance(new ComponentInstance("model", model));
        
        return system;
    }

    private boolean IsDisjunction(Expression expr)
    {
        if(expr instanceof BinaryExpression)
            return ((BinaryExpression)expr).Operator() instanceof LogicalOrOperator;
        else
            return false;
    }
}
