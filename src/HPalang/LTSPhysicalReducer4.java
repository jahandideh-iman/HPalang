/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang;

import HPalang.Core.TransitionSystem.LabeledTransitionSystem;
import HPalang.LTSGeneration.RunTimeStates.GlobalRunTimeState;
import HPalang.Core.TransitionSystem.Transition;
import java.util.HashSet;
import java.util.Set;
import HPalang.Core.TransitionSystem.StateWrapper;
import static HPalang.LTSPhyscialReducerUtilities.IsReducable;
import static HPalang.LTSPhyscialReducerUtilities.ReduceState;
import java.util.ArrayDeque;
import java.util.Queue;

/**
 *
 * @author Iman Jahandideh
 */
public class LTSPhysicalReducer4
{
    
    public LabeledTransitionSystem Reduce(LabeledTransitionSystem<GlobalRunTimeState> lts)
    {
        LabeledTransitionSystem<GlobalRunTimeState> resultLTS = new LabeledTransitionSystem<>();

        
        for(Transition<GlobalRunTimeState> tr : lts.Transitions())
        {
            StateWrapper<GlobalRunTimeState> newOrigin = resultLTS.TryAddState(tr.GetOrign().InnerState());
            StateWrapper<GlobalRunTimeState> newDest = resultLTS.TryAddState(tr.GetDestination().InnerState());
            resultLTS.AddTransition(newOrigin, tr.GetLabel(), newDest);
        }
        resultLTS.SetInitialState(lts.InitialState().InnerState());
        
        for(StateWrapper<GlobalRunTimeState> st : resultLTS.StateWrappers())
            if(IsReducable(st, resultLTS))
                ReduceState(st, resultLTS);

        //reducabledStates.forEach(st -> ReduceState(st, resultLTS));
        
        return resultLTS;
    }
    
}
