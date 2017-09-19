/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Convertors;

import HPalang.LTSGeneration.Label;
import HPalang.LTSGeneration.LabeledTransitionSystem;
import HPalang.LTSGeneration.RunTimeStates.GlobalRunTimeState;
import HPalang.LTSGeneration.Transition;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import static HPalang.Convertors.StringConversionUtilities.StringForCommonActorState;
import static HPalang.Convertors.StringConversionUtilities.StringForSoftwareActorState;
import static HPalang.Convertors.StringConversionUtilities.StringForDiscreteState;
import static HPalang.Convertors.StringConversionUtilities.StringFor;

/**
 *
 * @author Iman Jahandideh
 */
public class LTSToFSMConvertor
{
    private String output;
    
    private Map<GlobalRunTimeState, Integer> indexes;
    
    public String Convert(LabeledTransitionSystem lts)
    {
        output = "";
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
        
        for(Transition transition : lts.Transitions())
        {
            ConcatLine(String.format(
                    "%d %d %s ",
                    IndexFor(transition.GetOrign()),
                    IndexFor(transition.GetDestination()),
                    LabelFor(transition.GetLabel())
                    ));
        }
        
        return output;
    }
    
    private String CreateStatesValues(Set<GlobalRunTimeState> globalStates)
    {
        String values = "";
        
        for(GlobalRunTimeState gs : globalStates)
            values = values.concat(" " + Qoute(StringFor(gs)));

        return values;
    }
    
    private void ConcatLine(String line)
    {
        output =  output.concat(line + "\n");
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
