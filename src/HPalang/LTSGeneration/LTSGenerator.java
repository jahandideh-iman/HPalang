/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration;

import HPalang.LTSGeneration.RunTimeStates.GlobalRunTimeState;
import HPalang.LTSGeneration.SOSRules.SOSRule;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 *
 * @author Iman Jahandideh
 */
public class LTSGenerator
{
    private LabeledTransitionSystem transitionSystem;
        
    private Queue<GlobalRunTimeState> notVisitedStates = new LinkedList<>();
    
    private List<SOSRule> sosRules = new ArrayList<>();
    
    private GlobalRunTimeState currentGlobalState;
    
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
            currentGlobalState = notVisitedStates.poll();
            
            for(SOSRule rule : sosRules)
                rule.TryApply(currentGlobalState, this);
            
        }
        return transitionSystem;
    }
    
    public void AddTransition(Label label,GlobalRunTimeState destination)
    {
        if(transitionSystem.HasState(destination) == false)
            notVisitedStates.add(destination);
                
        transitionSystem.AddState(destination);
        transitionSystem.AddTransition(currentGlobalState, label, destination);
        if(transitionSystem.GetTransitions().size() == 25)
            transitionSystem.GetTransitions();       
    }
}
