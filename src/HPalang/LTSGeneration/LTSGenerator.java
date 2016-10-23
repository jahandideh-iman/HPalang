/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration;

import HPalang.LTSGeneration.RunTimeStates.ActorRunTimeState;
import HPalang.LTSGeneration.RunTimeStates.GlobalRunTimeState;
import HPalang.LTSGeneration.SOSRules.SOSRule;
import HPalang.Core.Actor;
import HPalang.Core.ProgramDefinition;
import HPalang.Statements.SendStatement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

/**
 *
 * @author Iman Jahandideh
 */
public class LTSGenerator
{
    private Queue<GlobalRunTimeState> notVisitedStates = new LinkedList<>();
    private Queue<GlobalRunTimeState> visitedStates = new LinkedList<>();
    
    private List<SOSRule> sosRules = new ArrayList<>();
    
    private LabeledTransitionSystem transitionSystem;
    private GlobalRunTimeState currentGlobalState;
    
    
    public LabeledTransitionSystem Generate(ProgramDefinition program)
    {
        transitionSystem = new LabeledTransitionSystem();
        currentGlobalState = CreateInitialState(program);
        transitionSystem.SetInitialState(currentGlobalState);
        notVisitedStates.add(currentGlobalState);
        
        while (!notVisitedStates.isEmpty()) 
        {
            GlobalRunTimeState currentState = notVisitedStates.poll();
            visitedStates.add(currentState);
            
            sosRules.forEach((rule) -> {
                rule.TryApply(currentState, this);
            });
        }
        
        return transitionSystem;
    }
    
    public void AddTransition(Label label,GlobalRunTimeState destination)
    {
        transitionSystem.AddState(destination);
        transitionSystem.AddTransition(currentGlobalState, label, destination);
        
        if(visitedStates.contains(destination) == false)
            notVisitedStates.add(destination);
    }
    
    private GlobalRunTimeState CreateInitialState(ProgramDefinition definition)
    {
        GlobalRunTimeState initialState = new GlobalRunTimeState();
        
        for(Actor actor : definition.GetActors())
            initialState.AddActorRunTimeState(new ActorRunTimeState(actor));
        
        for(SendStatement send : definition.GetInitialSends())
            initialState.AddSendStatement(send);
        
        return initialState;
    }

    public void AddSOSRule(SOSRule rule)
    {
        sosRules.add(rule);
    }

    public LabeledTransitionSystem Generate(GlobalRunTimeState initialState)
    {
        transitionSystem = new LabeledTransitionSystem();
        currentGlobalState = initialState;
        transitionSystem.SetInitialState(currentGlobalState);
        notVisitedStates.add(currentGlobalState);
        
        while (!notVisitedStates.isEmpty()) 
        {
            GlobalRunTimeState currentState = notVisitedStates.poll();
            visitedStates.add(currentState);
            
            for(SOSRule rule : sosRules)
                rule.TryApply(currentState, this);
        }
        
        return transitionSystem;
    }
}
