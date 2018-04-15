/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Convertors;

import HPalang.Core.TransitionSystem.Label;
import HPalang.Core.TransitionSystem.LabeledTransitionSystem;
import HPalang.LTSGeneration.RunTimeStates.GlobalRunTimeState;
import HPalang.Core.TransitionSystem.Transition;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import static HPalang.Convertors.StringConversionUtilities.StringFor;
import java.util.Collection;

/**
 *
 * @author Iman Jahandideh
 */
public class LTSToFSMConvertor
{
    private StringBuilder output;
    
    private Map<GlobalRunTimeState, Integer> indexes;
    
    public String Convert(LabeledTransitionSystem<GlobalRunTimeState> lts)
    {
        output = new StringBuilder();
        indexes = new HashMap<>();
        
        int i =1;
        for(GlobalRunTimeState state : lts.States())
        {
            indexes.put(state, i);
            i++;
        }
        
        ConcatLine(String.format("gs(%d) GState %s", lts.States().size(), CreateStatesValues(lts.States())));
        
        ConcatLine("---");
        
        for(GlobalRunTimeState gs : lts.States())
            ConcatLine(String.valueOf(IndexFor(gs)-1));
        
        ConcatLine("---");
        
        for(Transition<GlobalRunTimeState> transition : lts.Transitions())
        {
            ConcatLine(String.format(
                    "%d %d %s ",
                    IndexFor(transition.GetOrign().InnerState()),
                    IndexFor(transition.GetDestination().InnerState()),
                    LabelFor(transition.GetLabel())
                    ));
        }
        
        return output.toString();
    }
    
    private String CreateStatesValues(Collection<GlobalRunTimeState> globalStates)
    {
        StringBuilder values = new StringBuilder();
        
        for(GlobalRunTimeState gs : globalStates)
            values = values.append(" ").append(Qoute(StringFor(gs)));

        return values.toString();
    }
    
    private void ConcatLine(String line)
    {
        output =  output.append(line).append("\n");
    }
    
    private int IndexFor(GlobalRunTimeState state)
    {
        return indexes.get(state);
    }
    
    private String LabelFor(Label label)
    {
        String labelStr = StringFor(label);
        
        return  "\"" + labelStr +"\"";
    }
    
    private String Qoute(String str)
    {
        return "\"" + str +"\"";
    }
}
