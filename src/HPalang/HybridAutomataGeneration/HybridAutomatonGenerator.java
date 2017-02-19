/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.HybridAutomataGeneration;

import HPalang.Core.ProgramDefinition;
import HPalang.HybridAutomataGeneration.SOSRules.TransitionSOSRule;
import HPalang.LTSGeneration.GuardedlLabel;
import HPalang.LTSGeneration.Label;
import HPalang.LTSGeneration.LabeledTransitionSystem;
import HPalang.LTSGeneration.RunTimeStates.ActorRunTimeState;
import HPalang.LTSGeneration.RunTimeStates.ContinuousBehavior;
import HPalang.LTSGeneration.RunTimeStates.GlobalRunTimeState;
import HPalang.LTSGeneration.SOSRule;
import HPalang.LTSGeneration.Transition;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 *
 * @author Iman Jahandideh
 */
public class HybridAutomatonGenerator 
{
    private HybridAutomaton hybridAutomaton;
        
    
    private List<TransitionSOSRule> sosRules = new ArrayList<>();
    
    
    public void AddSOSRule(TransitionSOSRule rule)
    {
        sosRules.add(rule);
    }

    public HybridAutomaton Generate(LabeledTransitionSystem lts)
    {
        hybridAutomaton = new HybridAutomaton();
//        currentGlobalState = initialState;
//        transitionSystem.SetInitialState(currentGlobalState);
//        notVisitedStates.add(currentGlobalState);
        
        for(Transition transition : lts.GetTransitions())
        {
            for(TransitionSOSRule rule : sosRules)
                rule.TryApply(transition, this);
        }
        
        hybridAutomaton.SetInitialState(ToLocation(lts.GetInitialState()));
            
//        while (!notVisitedStates.isEmpty()) 
//        { 
//            currentGlobalState = notVisitedStates.poll();
//            
//            for(SOSRule rule : sosRules)
//                rule.TryApply(currentGlobalState, this);
//            
//        }
        return hybridAutomaton;
    }
    
//    public void AddTransition(Label label,GlobalRunTimeState destination)
//    {
//        if(transitionSystem.HasState(destination) == false)
//            notVisitedStates.add(destination);
//                
//        transitionSystem.AddState(destination);
//        transitionSystem.AddTransition(currentGlobalState, label, destination);
//        if(transitionSystem.GetTransitions().size() == 25)
//            transitionSystem.GetTransitions();       
//    }

    public void AddTransition(Location origin, GuardedlLabel label, Location destination)
    {
        hybridAutomaton.AddTransition(origin, label, destination);
    }
    
    public Location ToLocation(GlobalRunTimeState state)
    {
        Location location = new Location();
        
        for(ActorRunTimeState actorState : state.GetActorStates())
            for(ContinuousBehavior behavior : actorState.ContinuousBehaviors())
            {
                location.AddEquation(behavior.GetEquation());
                location.AddInvariant(behavior.GetInvarient());
            }
        
        return location;
    }
}
