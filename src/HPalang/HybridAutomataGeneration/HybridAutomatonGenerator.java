/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.HybridAutomataGeneration;

import HPalang.Core.Actor;
import HPalang.Core.ActorType;
import HPalang.Core.ExpressionScopeUnwrapper;
import HPalang.Core.ModelDefinition;
import HPalang.Core.Variable;
import HPalang.LTSGeneration.LabeledTransitionSystem;
import HPalang.LTSGeneration.RunTimeStates.GlobalRunTimeState;
import HPalang.LTSGeneration.StateInfo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Iman Jahandideh
 */
public class HybridAutomatonGenerator 
{
    private HybridAutomaton hybridAutomaton;
    
    private final List<SOSRule> sosRules = new ArrayList<>();
    private final Map<GlobalRunTimeState, Location> processedLocationsMap = new HashMap<>();
    
    public void AddSOSRule(SOSRule rule)
    {
        sosRules.add(rule);
    }

    public HybridAutomaton Generate(LabeledTransitionSystem lts, ModelDefinition modelDefinition)
    {
        hybridAutomaton = new HybridAutomaton();
        
        ConvertVariables(modelDefinition);

        for(GlobalRunTimeState globalState : lts.States())
        {

            for(SOSRule rule : sosRules)
                rule.TryApply(
                        new StateInfo(
                                globalState.DeepCopy(), 
                                lts.GetInTransitionFor(globalState),
                                lts.GetOutTransitionsFor(globalState)),
                        this);
        }
        
        for(HPalang.LTSGeneration.Transition transiton : lts.Transitions())
        {

            for(SOSRule rule : sosRules)
                rule.TryApply(
                        transiton,
                        this);
        }
        
        //hybridAutomaton.SetInitialState(LocationOf(lts.InitialState()));
            
        return hybridAutomaton;
    }
    
    public void AddLocationFor(Location location,GlobalRunTimeState runTimeState)
    {
        hybridAutomaton.AddLocation(location);
        processedLocationsMap.put(runTimeState, location);
    }
    
    public void AddTransition(Location origin, HybridLabel label, Location destination)
    {
        hybridAutomaton.AddTransition(origin, label, destination);
    }
    
    public Location LocationOf(GlobalRunTimeState state)
    {  
        if(processedLocationsMap.containsKey(state))
            return processedLocationsMap.get(state);
        
        throw new RuntimeException("State is not processed before.");
    }

    private void ConvertVariables(ModelDefinition definition)
    {
        if(definition == null)
            return;
        
        ExpressionScopeUnwrapper unwrapper = new ExpressionScopeUnwrapper();
        for(Actor actor : definition.Actors())
            for(Variable var : actor.Type().Variables())
                if(var.Type() == Variable.Type.floatingPoint || var.Type() == Variable.Type.real)
                    hybridAutomaton.AddVariable(unwrapper.Unwrap(var, actor.Name()).Name());
            
    }
    
    
}
