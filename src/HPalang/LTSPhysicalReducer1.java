/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang;

import HPalang.Core.TransitionSystem.LabeledTransitionSystem;
import HPalang.LTSGeneration.RunTimeStates.GlobalRunTimeState;
import HPalang.Core.TransitionSystem.Transition;
import java.util.ArrayList;
import java.util.List;
import HPalang.Core.TransitionSystem.StateWrapper;
import static HPalang.LTSPhyscialReducerUtilities.CopyTransitionSystem;
import static HPalang.LTSPhyscialReducerUtilities.IsReducable;
import static HPalang.LTSPhyscialReducerUtilities.ReduceState;

/**
 *
 * @author Iman Jahandideh
 */
public class LTSPhysicalReducer1
{
    
    public LabeledTransitionSystem Reduce(LabeledTransitionSystem<GlobalRunTimeState> lts)
    {
        LabeledTransitionSystem<GlobalRunTimeState> resultLTS = CopyTransitionSystem(lts);
        

        List<StateWrapper<GlobalRunTimeState>> reducabledStates = FindReducableStates(resultLTS);
        
        for(int i = 0 ; i< reducabledStates.size(); i++)
        {
            ReduceState(reducabledStates.get(i), resultLTS);
            if(i %1000 == 0)
                System.out.println("States so far " + i);
        }
        
        return resultLTS;
    }
    
    private List<StateWrapper<GlobalRunTimeState>> FindReducableStates(LabeledTransitionSystem<GlobalRunTimeState> lts)
    {
        List<StateWrapper<GlobalRunTimeState>> reducableStates = new ArrayList<>(lts.States().size());
        
        for(StateWrapper<GlobalRunTimeState> st : lts.StateWrappers())
            if(IsReducable(st, lts))
                reducableStates.add(st);
        
        return reducableStates;
    }

}
