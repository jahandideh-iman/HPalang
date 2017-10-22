/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Convertors;

import HPalang.Core.DifferentialEquation;
import HPalang.HybridAutomataGeneration.HybridAutomaton;
import HPalang.LTSGeneration.Labels.Reset;
import HPalang.SpaceEx.Convertor.SpaceExToXMLConvertor;
import HPalang.SpaceEx.Core.BaseComponent;
import HPalang.SpaceEx.Core.Flow;
import HPalang.SpaceEx.Core.HybridLabel;
import HPalang.Core.Invarient;
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
    public String Convert(HybridAutomaton hybridAutomaton)
    {
        SpaceExModel spaceExModel = ConvertHAToSpaceExModel(hybridAutomaton);
       
        
        
        return new SpaceExToXMLConvertor().Convert(spaceExModel);
    }
    
    private SpaceExModel ConvertHAToSpaceExModel(HybridAutomaton automaton)
    {
        SpaceExModel spaceExModel = new SpaceExModel();
        
        
        
        
        NetworkComponent system = new NetworkComponent("System");
        
        
        
        BaseComponent model = new BaseComponent("model");
        
        Map<HPalang.HybridAutomataGeneration.Location, HPalang.SpaceEx.Core.Location> locationsMap = new HashMap<>();
        
        int i = 0;
        for(HPalang.HybridAutomataGeneration.Location hybridLocation : automaton.GetLocations())
        {   
            HPalang.SpaceEx.Core.Location spaceExLocation = 
                    new HPalang.SpaceEx.Core.Location(String.format("loc_%d", i));
            
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
            
            spaceExLabel.AddGuard(hybridLabel.Guard().toString());
            
            hybridLabel.Resets().forEach((reset) -> spaceExLabel.AddAssignment(reset.toString()));
            
            
            model.AddTransition(
                    locationsMap.get(hybridTransition.GetOrign()),
                    spaceExLabel,
                    locationsMap.get(hybridTransition.GetDestination()));
       
        }
        
        for(String variable : automaton.Variables())
            model.AddParameter(new RealParameter(variable, true));
        
        spaceExModel.AddComponent(model);
        return spaceExModel;
    }

}
