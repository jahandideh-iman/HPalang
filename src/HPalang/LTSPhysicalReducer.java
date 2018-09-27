/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang;

import HPalang.Core.DiscreteExpressions.BinaryExpression;
import HPalang.Core.DiscreteExpressions.BinaryOperators.LogicalAndOperator;
import HPalang.Core.DiscreteExpressions.TrueConst;
import HPalang.Core.DiscreteExpressions.VariableExpression;
import HPalang.Core.Expression;
import HPalang.Core.TransitionSystem.Label;
import HPalang.Core.TransitionSystem.LabeledTransitionSystem;
import HPalang.LTSGeneration.Labels.ContinuousLabel;
import HPalang.LTSGeneration.Labels.SoftwareLabel;
import HPalang.LTSGeneration.RunTimeStates.GlobalRunTimeState;
import HPalang.Core.TransitionSystem.Transition;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import HPalang.Core.TransitionSystem.StateWrapper;
import HPalang.Core.Variable;
import HPalang.LTSGeneration.BasicPostOrderExpressionConvertor;
import HPalang.LTSGeneration.Labels.Guard;
import HPalang.LTSGeneration.Labels.Reset;
import static HPalang.LTSGeneration.SOSRules.Utilities.HasPhysicalActions;
import static HPalang.LTSGeneration.Utilities.QueryUtilities.IsDeadlock;
import java.util.HashMap;
import java.util.Map;
import static HPalang.LTSPhyscialReducerUtilities.CreateMergedLabelFor;

/**
 *
 * @author Iman Jahandideh
 */
public class LTSPhysicalReducer
{
    private LabeledTransitionSystem<GlobalRunTimeState> ltsToReduce;
    private LabeledTransitionSystem<GlobalRunTimeState> reducedLTS;
    private LabeledTransitionSystem<GlobalRunTimeState> mergedLTS;
    
    private Set<StateWrapper<GlobalRunTimeState>> visitedStates; 
    private Set<StateWrapper<GlobalRunTimeState>> notVisitedStates;
    
    private Set<Transition<GlobalRunTimeState>> visitedTransitions;
    
    private Map<Transition<GlobalRunTimeState>, List<Transition<GlobalRunTimeState>>> transitionsMaps;
    
    public LabeledTransitionSystem Reduce(LabeledTransitionSystem<GlobalRunTimeState> lts)
    {
        throw new RuntimeException("This is not correctly implmented yet.");
//        visitedStates = new HashSet<>();
//        notVisitedStates = new HashSet<>(lts.StateWrappers());
//        visitedTransitions = new HashSet<>();
//        ltsToReduce = lts;
//        mergedLTS = new LabeledTransitionSystem<>();
//        reducedLTS = new LabeledTransitionSystem<>();
//        transitionsMaps = new HashMap<>();
//        Visit(ltsToReduce.InitialState());
//        
//        reducedLTS.SetInitialState(lts.InitialState().InnerState());
//        
//        //RemoveUnreachableStates(reducedLTS);
//        
//        return reducedLTS;
    }
    
    private void Visit(StateWrapper<GlobalRunTimeState> wrapper)
    {
        for(Transition<GlobalRunTimeState> tran : wrapper.OutTransitions())
            Visit(tran, new ArrayList<>());
    }
    
    private void Visit(Transition<GlobalRunTimeState> lastTransition, List<Transition<GlobalRunTimeState>> transitionsSoFar)
    {
        if(visitedTransitions.contains(lastTransition))
        {
//            GlobalRunTimeState origin = lastTransition.GetOrign().InnerState();
//            StateWrapper<GlobalRunTimeState> p = reducedLTS.WrapperFor(origin);
//            List<Transition<GlobalRunTimeState>> transitions =  MergeArray(transitionsSoFar, lastTransition);
//            
//            p.OutTransitions().forEach( t -> Merge(MergeArray(transitions, t)));
//            
            return;
        }
        
        visitedTransitions.add(lastTransition);
        
        StateWrapper<GlobalRunTimeState> dest = lastTransition.GetDestination();
        
        if(IsPhysicalOrDeadlock(dest))
        {
            List<Transition<GlobalRunTimeState>> transitions = new ArrayList<>(transitionsSoFar);
            transitions.add(lastTransition);
            Merge(transitions);
            Visit(lastTransition.GetDestination());
        }
        else
        {
            for(Transition<GlobalRunTimeState> tran : dest.OutTransitions())
            {
                List<Transition<GlobalRunTimeState>> newTransitionSoFar = new ArrayList<>(transitionsSoFar);
                newTransitionSoFar.add(lastTransition);
                
                Visit(tran, newTransitionSoFar);
            }
        }
    }


    private boolean IsPhysicalOrDeadlock(StateWrapper<GlobalRunTimeState> originalWrapper)
    {
        return HasPhysicalActions(originalWrapper.OutTransitions()) || 
                IsDeadlock(originalWrapper.InnerState()) ||
                originalWrapper.OutTransitions().isEmpty();
    }

    private void Merge(List<Transition<GlobalRunTimeState>> transitions)
    {
        StateWrapper<GlobalRunTimeState> newOrign = reducedLTS.TryAddState(transitions.get(0).GetOrign().InnerState());
        StateWrapper<GlobalRunTimeState> newDest = reducedLTS.TryAddState(transitions.get(transitions.size()-1).GetDestination().InnerState());
        
        Label label = CreateMergedLabelFor(transitions);
        
        reducedLTS.AddTransition(
                newOrign,
                label, 
                newDest);
    }

}
