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
import static HPalang.Core.ModelCreationUtilities.CreateInvarient;
import HPalang.Core.Variables.RealVariable;
import HPalang.SpaceEx.Convertor.ExpressionConvertor;
import HPalang.SpaceEx.Core.Component;
import HPalang.SpaceEx.Core.ComponentInstance;
import HPalang.SpaceEx.Core.HybridTransition;
import HPalang.SpaceEx.Core.Location;
import HPalang.SpaceEx.Core.NetworkComponent;
import HPalang.SpaceEx.Core.RealParameter;
import HPalang.SpaceEx.Core.SpaceExModel;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Iman Jahandideh
 */
public class HybridAutomatonToSXConvertor
{
    
    private ExpressionConvertor expressionConvertor = new ExpressionConvertor();
    
    
    
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
        BaseComponent model = new BaseComponent("model");
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
        }
        for(HPalang.HybridAutomataGeneration.Transition hybridTransition : automaton.GetTransitions())
        {
            HPalang.HybridAutomataGeneration.HybridLabel hybridLabel = hybridTransition.GetLabel();
            
            HPalang.SpaceEx.Core.HybridLabel spaceExLabel = new HybridLabel();
            
            spaceExLabel.AddGuard(expressionConvertor.Convert(hybridLabel.Guard()));
            
            hybridLabel.Resets().forEach((reset) -> spaceExLabel.AddAssignment(expressionConvertor.Convert(reset)));
            
            
            model.AddTransition(
                    new HybridTransition(
                            locationsMap.get(hybridTransition.GetOrign()),
                            spaceExLabel,
                            locationsMap.get(hybridTransition.GetDestination()),
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
        
        timerComp.AddParameter(new RealParameter("duration", false, RealParameter.Dynamic.Const));       
        timerComp.AddParameter(new RealParameter("time", false));
        
        Location loc1 = new Location("loc1");
        Location loc2 = new Location("loc2");
        
        loc1.AddInvarient(CreateInvarient(new RealVariable("time"), "<=", new RealVariable("duration")));
        loc1.AddFlow(new Flow("time' == 1"));
        
        loc2.AddFlow(new Flow("time' == 0"));
        
        timerComp.AddTransition(loc1, new HybridLabel().AddGuard("time == duration"), loc2);

        
        return timerComp;
    }
    

    private Component CreateSystemComponent(Component timer, Component model)
    {
        NetworkComponent system = new NetworkComponent("System");
        ComponentInstance timerInst = new ComponentInstance("timer", timer);
        system.AddParameter(new RealParameter("time", false));
        timerInst.SetBinding("time", "time");
        timerInst.SetBinding("duration", "15");
        system.AddInstance(timerInst);
        
        system.AddInstance(new ComponentInstance("model", model));
        
        return system;
    }



}
