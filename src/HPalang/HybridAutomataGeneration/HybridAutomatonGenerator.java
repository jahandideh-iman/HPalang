/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.HybridAutomataGeneration;

import HPalang.Core.Actor;
import HPalang.Core.ActorType;
import HPalang.Core.Message;
import HPalang.Core.MessageHandler;
import HPalang.LTSGeneration.ExpressionScopeUnwrapper;
import HPalang.Core.ModelDefinition;
import HPalang.Core.Variable;
import HPalang.Core.TransitionSystem.LabeledTransitionSystem;
import HPalang.LTSGeneration.RunTimeStates.GlobalRunTimeState;
import HPalang.LTSGeneration.StateInfo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import HPalang.Core.VariableParameter;
import static HPalang.LTSGeneration.SOSRules.Utilities.UnWrapExpressionScope;
import static HPalang.LTSGeneration.SOSRules.Utilities.UnWrapVariableScope;
import HPalang.Core.TransitionSystem.StateWrapper;

/**
 *
 * @author Iman Jahandideh
 */
public class HybridAutomatonGenerator 
{
    private HybridAutomaton hybridAutomaton;
    
    private final List<SOSRule> sosRules = new ArrayList<>();
    private final Map<GlobalRunTimeState, Location> processedLocationsMap = new HashMap<>();
    
    private int idPostFix = 0;
    
    private LabeledTransitionSystem lts;
    
    public void AddSOSRule(SOSRule rule)
    {
        sosRules.add(rule);
    }

    
    // TODO: Refactor this crap.
    public HybridAutomaton Generate(LabeledTransitionSystem<GlobalRunTimeState> lts, ModelDefinition modelDefinition)
    {
        this.lts = lts;
        hybridAutomaton = new HybridAutomaton();
        idPostFix = 0;
        
        ConvertVariables(modelDefinition, lts.InitialState().InnerState());

        int counter = 0;
        for(StateWrapper<GlobalRunTimeState> globalState : lts.StateWrappers())
        {
            ApplyRulesOn(globalState);
            counter++;
            if(counter%5000 == 0)
                System.out.println("States visited: " + counter);
        }
        counter = 0;
        for(HPalang.Core.TransitionSystem.Transition transiton : lts.Transitions())
        {

            for(SOSRule rule : sosRules)
                rule.TryApply(
                        transiton,
                        this);
            counter++;
            if(counter%5000 == 0)
                System.out.println("Transitions visited: " + counter);
        }
        
        hybridAutomaton.SetInitialState(LocationOf(lts.InitialState().InnerState()));
           
        return hybridAutomaton;
    }

    private void ApplyRulesOn(StateWrapper<GlobalRunTimeState> globalState)
    {
        for(SOSRule rule : sosRules)
            rule.TryApply(
                    new StateInfo(
                            globalState.InnerState().DeepCopy(),
                            globalState.InTransitions(),
                            globalState.OutTransitions()),
                    this);
    }
    
    public void AddLocationFor(Location location,GlobalRunTimeState runTimeState)
    {
        hybridAutomaton.TryAddState(location);
        processedLocationsMap.put(runTimeState, location);
        idPostFix++;
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

    private void ConvertVariables(ModelDefinition definition, GlobalRunTimeState initialState)
    {
        if(definition == null)
            return;
        
        for(Actor actor : definition.Actors())
        {
            for(Variable var : actor.Type().Variables())
            {
                if(var.Type() == Variable.Type.real || var.Type() == Variable.Type.floatingPoint)
                    hybridAutomaton.AddVariable(UnWrapVariableScope(var, actor).Name());
            }
            for(MessageHandler handler : actor.Type().MessageHandlers())
            {
                for(VariableParameter param : handler.Parameters().AsList())
                    hybridAutomaton.AddVariable(UnWrapVariableScope(param.Variable(), actor).Name());
            }
        }
        
        for(Variable var : initialState.EventsState().PoolState().Pool().AllVariables())
            hybridAutomaton.AddVariable(var.Name());
        
        for(Variable var : initialState.VariablePoolState().Pool().AllVariables())
            hybridAutomaton.AddVariable(var.Name());
        
        hybridAutomaton.AddVariable("urg");
    }
    
    
    
    public String CreateAUniqueLocationName(GlobalRunTimeState state)
    {
        if(lts.InitialState().InnerState().equals(state))
            return "InitalLoc";
        return String.format("Loc_%d", idPostFix);
    }
    
    
}
