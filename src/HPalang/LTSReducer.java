/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang;

import HPalang.LTSGeneration.Label;
import HPalang.LTSGeneration.LabeledTransitionSystem;
import HPalang.LTSGeneration.Labels.ContinuousLabel;
import HPalang.LTSGeneration.Labels.SoftwareLabel;
import HPalang.LTSGeneration.RunTimeStates.GlobalRunTimeState;
import HPalang.LTSGeneration.Transition;
import static HPalang.Main.CreateLabelFor;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Iman Jahandideh
 */
public class LTSReducer
{
    private LabeledTransitionSystem ltsToReduce;
    private LabeledTransitionSystem reducedLTS;
    
    private Set<GlobalRunTimeState> visitedStates;
    
    public LabeledTransitionSystem Reduce(LabeledTransitionSystem lts)
    {
        visitedStates = new HashSet<>();
        ltsToReduce = lts;
        reducedLTS = new LabeledTransitionSystem();

        
        List<Transition> initalOutTrans = ltsToReduce.GetOutTransitionsFor(ltsToReduce.InitialState());

        for(Transition tran : initalOutTrans)
        {
            Recurse(lts.InitialState(), tran.GetLabel(), tran.GetDestination(), Collections.EMPTY_LIST);
        }
        
        reducedLTS.SetInitialState(lts.InitialState());
  
        return reducedLTS;
    }
    
    
    private void Recurse(GlobalRunTimeState orignState, Label orignLabel ,GlobalRunTimeState currentState, List<Label> labelsSoFar)
    {
        List<Transition> outTrans = ltsToReduce.GetOutTransitionsFor(currentState);
        
        for(Transition tran : outTrans)
        {
            if(tran.GetLabel() instanceof ContinuousLabel)
            {
                reducedLTS.AddTransition(orignState, tran.GetLabel(), tran.GetDestination());
                Recurse(currentState, tran.GetLabel(), tran.GetDestination(), Collections.EMPTY_LIST);
            }
            else
            {
                List<Label> newlabels = new LinkedList<>(labelsSoFar);
                newlabels.add(tran.GetLabel());
                Recurse(orignState, orignLabel , tran.GetDestination(),newlabels);
            }
            
        }
    }
}
