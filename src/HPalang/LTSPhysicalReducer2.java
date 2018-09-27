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
import static HPalang.LTSPhyscialReducerUtilities.CopyTransitionSystem;
import static HPalang.LTSPhyscialReducerUtilities.IsReducable;
import static HPalang.LTSPhyscialReducerUtilities.ReduceState;
import java.util.ArrayDeque;
import java.util.Queue;

/**
 *
 * @author Iman Jahandideh
 */
public class LTSPhysicalReducer2
{
    
    public LabeledTransitionSystem Reduce(LabeledTransitionSystem<GlobalRunTimeState> lts)
    {   
        Set<StateWrapper<GlobalRunTimeState>> visitedStates = new HashSet<>();
        Set<StateWrapper<GlobalRunTimeState>> notVisitedStates = new HashSet<>();
        Queue<StateWrapper<GlobalRunTimeState>> notVisitedStatesQueue = new ArrayDeque<>();

        LabeledTransitionSystem<GlobalRunTimeState> resultLTS = CopyTransitionSystem(lts);
        
        
        notVisitedStatesQueue.add(resultLTS.InitialState());
        
        while(notVisitedStatesQueue.size() > 0)
        {
            StateWrapper<GlobalRunTimeState> st = notVisitedStatesQueue.poll();
            
            if(IsReducable(st, resultLTS))
            {
                ReduceState(st, resultLTS);
            }
            
            visitedStates.add(st);
            
            for(Transition<GlobalRunTimeState> tr : st.OutTransitions())
                if(visitedStates.contains(tr.GetDestination()) == false
                        && notVisitedStates.contains(tr.GetDestination()) == false)
                {
                    notVisitedStatesQueue.add(tr.GetDestination());
                    notVisitedStates.add(tr.GetDestination());
                }
            
            if(visitedStates.size() % 1000 == 0)
                System.out.println("States so far " + visitedStates.size());
        }

        //reducabledStates.forEach(st -> ReduceState(st, resultLTS));
        
        return resultLTS;
    }


    
}
