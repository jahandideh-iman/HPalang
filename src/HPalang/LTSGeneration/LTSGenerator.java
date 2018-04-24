/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration;

import HPalang.Convertors.StringConversionUtilities;
import HPalang.Core.TransitionSystem.Label;
import HPalang.Core.TransitionSystem.LabeledTransitionSystem;
import static HPalang.LTSGeneration.Utilities.QueryUtilities.IsDeadlock;
import HPalang.LTSGeneration.RunTimeStates.GlobalRunTimeState;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 *
 * @author Iman Jahandideh
 */
public class LTSGenerator implements TransitionCollector
{
    private LabeledTransitionSystem<GlobalRunTimeState> transitionSystem;
        
    private final Queue<HPalang.Core.TransitionSystem.StateWrapper<GlobalRunTimeState>> notVisitedStates = new LinkedList<>();
    
    private final List<SOSRule> sosRules = new ArrayList<>();
    
    private HPalang.Core.TransitionSystem.StateWrapper<GlobalRunTimeState> currentGlobalState;
    
    public void AddSOSRule(SOSRule rule)
    {
        sosRules.add(rule);
    }

    public LabeledTransitionSystem Generate(GlobalRunTimeState initialState)
    {
        //TransitionSystemGlobalRunTimeState transitionInitalState = new TransitionSystemGlobalRunTimeState<Glo>(initialState);
        transitionSystem = new LabeledTransitionSystem<>();
        
        currentGlobalState = transitionSystem.TryAddState(initialState);
        transitionSystem.SetInitialState(initialState);
        notVisitedStates.add(currentGlobalState);
        
        while (!notVisitedStates.isEmpty()) 
        { 
            currentGlobalState = notVisitedStates.poll();
            
            if(IsDeadlock(currentGlobalState.InnerState()))
                continue;
            
            for(SOSRule rule : sosRules)
                rule.TryApply(
                        new StateInfo(
                                currentGlobalState.InnerState().DeepCopy(), 
                                currentGlobalState.InTransitions(),
                                currentGlobalState.OutTransitions()),
                        this);
            
            
        }
        return transitionSystem;
    }
    
    @Override
    public void AddTransition(Label label,GlobalRunTimeState destination)
    {
        if(transitionSystem.HasState(destination) == false)
        {
            //System.out.println(StringConversionUtilities.StringFor(destination));
            notVisitedStates.add(transitionSystem.TryAddState(destination));
        }
        
        
        transitionSystem.AddTransition(currentGlobalState, label, transitionSystem.TryAddState(destination));
        
        if (transitionSystem.TransitionsSize() % 5000 == 0) 
        {
            System.out.println("States so far: " + transitionSystem.States().size());
            System.out.println("Transitions so far: " + transitionSystem.Transitions().size());
            System.out.println("------------------------------------------");
        }
    }
}
