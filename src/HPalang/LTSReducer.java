/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang;

import HPalang.Core.TransitionSystem.Label;
import HPalang.Core.TransitionSystem.LabeledTransitionSystem;
import HPalang.LTSGeneration.Labels.ContinuousLabel;
import HPalang.LTSGeneration.Labels.SoftwareLabel;
import HPalang.LTSGeneration.RunTimeStates.GlobalRunTimeState;
import HPalang.Core.TransitionSystem.Transition;
import static HPalang.Main.CreateLabelFor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;
import HPalang.Core.TransitionSystem.StateWrapper;
import java.util.Collection;

/**
 *
 * @author Iman Jahandideh
 */
public class LTSReducer
{
//    private LabeledTransitionSystem<GlobalRunTimeState> ltsToReduce;
//    private LabeledTransitionSystem<GlobalRunTimeState> reducedLTS;
//    
//    private Set<StateWrapper<GlobalRunTimeState>> visitedStates; 
//    private Set<StateWrapper<GlobalRunTimeState>> notVisitedStates;
//    
//    public LabeledTransitionSystem Reduce(LabeledTransitionSystem<GlobalRunTimeState> lts)
//    {
//        visitedStates = new HashSet<>();
//        notVisitedStates = new HashSet<>(lts.StateWrappers());
//        ltsToReduce = lts;
//        reducedLTS = new LabeledTransitionSystem<>();
//        
//        Visit(ltsToReduce.InitialState());
//        
//        reducedLTS.SetInitialState(lts.InitialState().InnerState());
//        
//        RemoveUnreachableStates(reducedLTS);
//        
//        return reducedLTS;
//    }
//    
//    private void Visit(StateWrapper<GlobalRunTimeState> originalWrapper)
//    {
//        if(visitedStates.contains(originalWrapper))
//            return;
//        StateWrapper<GlobalRunTimeState> newWrapper =  reducedLTS.TryAddState(originalWrapper.InnerState());
//        visitedStates.add(originalWrapper);
//        
//        Collection<Transition<GlobalRunTimeState>> outTrans = originalWrapper.OutTransitions();
//        
//        ReduceTransitionsFrom(newWrapper);
//        
//        for(Transition<GlobalRunTimeState> tran : outTrans)
//            Visit(tran.GetDestination().InnerState());
//        
//    }
//
//    private void ReduceTransitionsFrom(StateWrapper<GlobalRunTimeState> staeWrapper)
//    {
//        Collection<Transition<GlobalRunTimeState>> outTrans = staeWrapper.OutTransitions();
//        
//        for(Transition<GlobalRunTimeState> tran : outTrans)
//        {
//            ReduceMaximumLengthTransitions(new LinkedList<>(Arrays.asList(tran)));
//        }
//        
//        
//    }
//
//    private void ReduceMaximumLengthTransitions(Deque<Transition<GlobalRunTimeState>> transitionChain)
//    {
//        Transition lastTransition = transitionChain.peekLast();
//        StateWrapper<GlobalRunTimeState> lastState = lastTransition.GetDestination();
//        
//        List<Transition> outTrans = ltsToReduce.GetOutTransitionsFor(lastState);
//        
//        for(Transition tran : outTrans)
//        {
//            if(IsReducable(tran))
//            {
//                Deque<Transition> newChain = new LinkedList<>(transitionChain);
//                newChain.addLast(tran);
//                ReduceMaximumLengthTransitions(newChain);
//            }
//            else
//                ReduceTransitions(transitionChain);
//        }
//    }
//
//    private boolean IsReducable(Transition tran)
//    {
//        if(tran.GetLabel() instanceof ContinuousLabel)
//            return false;
//        
//        //return true;
//        return tran.GetLabel().IsGuarded() == false && tran.GetLabel().Resets().isEmpty();
//    }
//
//    private void ReduceTransitions(Deque<Transition> transitionChain)
//    {
//        GlobalRunTimeState firstState = transitionChain.peekFirst().GetOrign();
//        
//        GlobalRunTimeState lastState = transitionChain.peekLast().GetDestination();
//        
//        Transition firstTransition = transitionChain.peekFirst();
//        
//        reducedLTS.AddTransition(firstState, firstTransition.GetLabel(), lastState);
//    }
//
//    private static void RemoveUnreachableStates(LabeledTransitionSystem lts)
//    {
//        List<GlobalRunTimeState> reachableStates = new LinkedList<>();
//        Queue<GlobalRunTimeState> notVisitedStates = new LinkedList<>();
//        Queue<GlobalRunTimeState> visitedStates = new LinkedList<>();
//        
//        notVisitedStates.add(lts.InitialState());
//        
//        while(notVisitedStates.isEmpty() == false)
//        {
//            GlobalRunTimeState state = notVisitedStates.poll();
//            reachableStates.add(state);
//            visitedStates.add(state);
//            
//            for(Transition t : lts.GetOutTransitionsFor(state))
//                if(visitedStates.contains(t.GetDestination()) == false
//                        && notVisitedStates.contains(t.GetDestination()) == false)
//                    notVisitedStates.add(t.GetDestination());
//        }
//        
//        for(GlobalRunTimeState state : new ArrayList<>(lts.States()))
//            if(reachableStates.contains(state) == false)
//                lts.RemoveState(state);
//        
//    }
}
