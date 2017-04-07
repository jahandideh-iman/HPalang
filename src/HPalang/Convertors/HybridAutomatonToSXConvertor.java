/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Convertors;

import HPalang.HybridAutomataGeneration.HybridAutomaton;
import HPalang.SpaceEx.Convertor.SpaceExToXMLConvertor;
import HPalang.SpaceEx.Core.BaseComponent;
import HPalang.SpaceEx.Core.NetworkComponent;
import HPalang.SpaceEx.Core.SpaceExModel;

/**
 *
 * @author Iman Jahandideh
 */
public class HybridAutomatonToSXConvertor
{
    public String Convert(HybridAutomaton hybridAutomaton)
    {
        SpaceExModel spaceExModel = new SpaceExModel();
       
        
        return new SpaceExToXMLConvertor().Convert(spaceExModel);
    }
    
    public SpaceExModel FromHybridAutomaton(HybridAutomaton automaton)
    {
        SpaceExModel spaceExModel = new SpaceExModel();
        
        NetworkComponent system = new NetworkComponent("System");
        
        BaseComponent model = new BaseComponent("model");
        
        
        
        return spaceExModel;
    }

}
