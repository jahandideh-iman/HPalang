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
import static HPalang.LTSPhyscialReducerUtilities.CreateMergedLabelFor;
import static HPalang.LTSPhyscialReducerUtilities.IsPhysicalOrDeadlock;
import static HPalang.LTSPhyscialReducerUtilities.IsReducable;
import com.sun.org.apache.bcel.internal.generic.GotoInstruction;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

/**
 *
 * @author Iman Jahandideh
 */
public class LTSPhysicalReducer3
{
    
    private Map<StateWrapper<GlobalRunTimeState>, Collection<Transition<GlobalRunTimeState>>> transitionsCache;
    public LabeledTransitionSystem Reduce(LabeledTransitionSystem<GlobalRunTimeState> lts)
    {
        transitionsCache = new HashMap<>();
        LabeledTransitionSystem<GlobalRunTimeState> resultLTS = new LabeledTransitionSystem<>();
                
        List<StateWrapper<GlobalRunTimeState>> nonReducableStates = new ArrayList<>();

//        for(Transition<GlobalRunTimeState> tr : lts.Transitions())
//        {
//            StateWrapper<GlobalRunTimeState> newOrigin = resultLTS.TryAddState(tr.GetOrign().InnerState());
//            StateWrapper<GlobalRunTimeState> newDest = resultLTS.TryAddState(tr.GetDestination().InnerState());
//            resultLTS.AddTransition(newOrigin, tr.GetLabel(), newDest);
//        }
//        resultLTS.SetInitialState(lts.InitialState().InnerState());
//        
        for(StateWrapper<GlobalRunTimeState> st : lts.StateWrappers())
            if(IsReducable(st, lts) == false)
                nonReducableStates.add(st);
        
        for(int i = 0 ; i< nonReducableStates.size(); i++)
        {
            StartReducingFrom(nonReducableStates.get(i),resultLTS, lts);
            if(i % 1000 == 0)
                System.out.println("States so far " + i);
        }

        
        resultLTS.SetInitialState(lts.InitialState().InnerState());
        
        return resultLTS;
    }
    
    private void StartReducingFrom(StateWrapper<GlobalRunTimeState> st, LabeledTransitionSystem<GlobalRunTimeState> toBeMergedLTS, LabeledTransitionSystem<GlobalRunTimeState> orignalLTS)
    {
        
        StateWrapper<GlobalRunTimeState> newState = toBeMergedLTS.TryAddState(st.InnerState());
        
        for(Transition<GlobalRunTimeState> outTr : st.OutTransitions())
        {
        Collection<Transition<GlobalRunTimeState>> outMergedTrans = MergedTransitionsFrom(outTr.GetDestination(), orignalLTS);
        
            for(Transition<GlobalRunTimeState> outMergedTr : outMergedTrans)
            {
                Label mergedLabled = CreateMergedLabelFor(Arrays.asList(outTr, outMergedTr));
                
                StateWrapper<GlobalRunTimeState> newDest = toBeMergedLTS.TryAddState(outMergedTr.GetDestination().InnerState());

                toBeMergedLTS.AddTransition(newState, mergedLabled, newDest);
            }
        }
    }

    private Collection<Transition<GlobalRunTimeState>> MergedTransitionsFrom(StateWrapper<GlobalRunTimeState> st, LabeledTransitionSystem<GlobalRunTimeState> lts)
    {
        if(IsReducable(st, lts) == false)
            return Collections.EMPTY_LIST;
        
        if(transitionsCache.containsKey(st))
        {
            return transitionsCache.get(st);
        }
        
        Collection<Transition<GlobalRunTimeState>> mergedTransitions = new ArrayList<>(st.OutTransitions().size());
        for(Transition<GlobalRunTimeState> tr : st.OutTransitions())
        {
            Collection<Transition<GlobalRunTimeState>> outMergedTransitions = MergedTransitionsFrom(tr.GetDestination(), lts);
            
            if(outMergedTransitions.isEmpty())
                mergedTransitions.add(tr);
            else
            {
                for(Transition<GlobalRunTimeState> omTr : outMergedTransitions)
                {
                    Label mergedLabled = CreateMergedLabelFor(Arrays.asList(tr,omTr));
                    Transition<GlobalRunTimeState> mergedTr = 
                            new Transition<>(st, mergedLabled, omTr.GetDestination());
                    
                    mergedTransitions.add(mergedTr);
                }
            }
        }
            
        
        transitionsCache.put(st, mergedTransitions);

        //System.out.println("States so far " + transitionsCache.size());
        return mergedTransitions;
    }

}
