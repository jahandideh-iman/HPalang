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
import HPalang.Core.TransitionSystem.StateWrapper;
import HPalang.Core.TransitionSystem.Transition;
import HPalang.Core.Variable;
import HPalang.LTSGeneration.BasicPostOrderExpressionConvertor;
import HPalang.LTSGeneration.Labels.ContinuousLabel;
import HPalang.LTSGeneration.Labels.Guard;
import HPalang.LTSGeneration.Labels.Reset;
import HPalang.LTSGeneration.Labels.SoftwareLabel;
import HPalang.LTSGeneration.RunTimeStates.GlobalRunTimeState;
import static HPalang.LTSGeneration.SOSRules.Utilities.HasPhysicalActions;
import static HPalang.LTSGeneration.Utilities.QueryUtilities.IsDeadlock;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Iman Jahandideh
 */
public  class LTSPhyscialReducerUtilities
{
    public static LabeledTransitionSystem<GlobalRunTimeState> CopyTransitionSystem(LabeledTransitionSystem<GlobalRunTimeState> lts)
    {
        LabeledTransitionSystem<GlobalRunTimeState> resultLTS = new LabeledTransitionSystem<>();
        for(Transition<GlobalRunTimeState> tr : lts.Transitions())
        {
            StateWrapper<GlobalRunTimeState> newOrigin = resultLTS.TryAddState(tr.GetOrign().InnerState());
            StateWrapper<GlobalRunTimeState> newDest = resultLTS.TryAddState(tr.GetDestination().InnerState());
            resultLTS.AddTransition(newOrigin, tr.GetLabel(), newDest);
        }
        resultLTS.SetInitialState(lts.InitialState().InnerState());
        return resultLTS;
    }
    
    public static boolean IsPhysicalOrDeadlock(StateWrapper<GlobalRunTimeState> originalWrapper)
    {
        return HasPhysicalActions(originalWrapper.OutTransitions()) || 
                IsDeadlock(originalWrapper.InnerState()) ||
                originalWrapper.OutTransitions().isEmpty();
    }
    
   
    public static boolean IsReducable(StateWrapper<GlobalRunTimeState> st, LabeledTransitionSystem<GlobalRunTimeState> lts)
    {
        if(IsPhysicalOrDeadlock(st))
            return false;
        
        if(lts.InitialState().equals(st))
            return false;
        
        return true;
    }

    
    public static void ReduceState(StateWrapper<GlobalRunTimeState> stateToBeReduced, LabeledTransitionSystem<GlobalRunTimeState> lts)
    {
        for(Transition<GlobalRunTimeState> in : stateToBeReduced.InTransitions())
        {
            for(Transition<GlobalRunTimeState> out : stateToBeReduced.OutTransitions())
            {
                Label label = CreateMergedLabelFor(Arrays.asList(in, out));
                lts.AddTransition(in.GetOrign(), label, out.GetDestination());
            }
        }
        lts.RemoveState(stateToBeReduced);
    }


    public static Label CreateMergedLabelFor(List<Transition<GlobalRunTimeState>> transitions)
    {
        List<Guard> guards = new ArrayList<>(transitions.size());
        List<Set<Reset>> resets = new ArrayList<>(transitions.size());
        
        for(Transition<GlobalRunTimeState> tran : transitions)
        {
            Label label = tran.GetLabel();
            if(label.IsGuarded())
                guards.add(label.Guard());
            
            resets.add(label.Resets());
            
        }
        
        Guard mergedGuard = MergeGuards(guards);
        Set<Reset> mergesResets = MergeResets(resets);
        
        if(FirstStateIsPhysical(transitions))
            return new ContinuousLabel(mergedGuard, mergesResets);
        else
            return new SoftwareLabel(mergedGuard, mergesResets);
    }
    
    private static boolean FirstStateIsPhysical(List<Transition<GlobalRunTimeState>> transitions)
    {
        return HasPhysicalActions(transitions.get(0).GetOrign().OutTransitions());
    }

    private static Guard MergeGuards(List<Guard> guards)
    {
        if(guards.isEmpty())
            return null;
        
        Expression mergedGuardExp = guards.get(0).Expression();
        
        for(int i = 1 ; i< guards.size(); ++i)
        {
            
            mergedGuardExp = 
                    new BinaryExpression(
                            mergedGuardExp,
                            new LogicalAndOperator(),
                            guards.get(i).Expression());
        }
        
        return new Guard(mergedGuardExp);
    }

    private static Set<Reset> MergeResets(List<Set<Reset>> resets)
    {
        Map<Variable, Expression> latestVariableResets = new HashMap<>();
        
        for(Set<Reset> set : resets)
            for(Reset r : set)
            {
                if(latestVariableResets.containsKey(r.Variable()))
                {
                    Expression newExpr =  new VariableReplacer().Replace(latestVariableResets, r.Expression()) ;
                    
                    latestVariableResets.put(r.Variable(), newExpr);
                }
                else
                    latestVariableResets.put(r.Variable(), r.Expression());
            }
        
        HashSet<Reset> convertedResets = new HashSet<>();
        latestVariableResets.entrySet().forEach((entry) -> {
            Variable key = entry.getKey();
            Expression value = entry.getValue();
            convertedResets.add(new Reset(key, value));
        });
        return convertedResets;
    }
    
    private static class VariableReplacer extends BasicPostOrderExpressionConvertor
    {
        private Map<Variable,Expression> substitudes;
        
        public Expression Replace(Map<Variable,Expression> substitudes, Expression expression )
        {
            this.substitudes = substitudes;
            InitStack();
            expression.Visit(this);
            return Result();
        }

        @Override
        protected void Process(VariableExpression expr)
        {
            VariableExpression vExpr = (VariableExpression) expr;
            
            if(substitudes.containsKey(vExpr.Variable()))
                Push(substitudes.get(vExpr.Variable()));
            else
                Push(expr);
        }
        
        
    }
    
    public static <T> ArrayList<T> MergeArray(List<T> list, T elem)
    {
        ArrayList<T> newList = new ArrayList<>(list);
        newList.add(elem);
        return newList;
    }
     
    public static <T> ArrayList<T> MergeArray( List<T> list1, List<T> list2)
    {
        ArrayList<T> newList = new ArrayList<>(list1);
        newList.addAll(list2);
        return newList;
    }
}
