/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Convertors;

import HPalang.LTSGeneration.Label;
import HPalang.LTSGeneration.LabeledTransitionSystem;
import HPalang.LTSGeneration.RunTimeStates.GlobalRunTimeState;
import HPalang.LTSGeneration.State;
import HPalang.LTSGeneration.Transition;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;
import static HPalang.Convertors.StringConversionUtilities.*;

/**
 *
 * @author Iman Jahandideh
 */
public class LTSToAUTConvertor
{
    private Map<GlobalRunTimeState, Integer> indexes;
    private StringBuilder output;
    
    public String Convert(LabeledTransitionSystem lts)
    {
        output = new StringBuilder();
        
        indexes = new HashMap<>();
        
        indexes.put(lts.InitialState(), 0);
        
        int i =1;
        for(GlobalRunTimeState state : lts.States())
        {
            if(state != lts.InitialState())
            {
                indexes.put(state,i);
                i++;
            }
        }

        ConcatLine(String.format(
                "des (0, %d, %d) ",
                lts.Transitions().size(),
                lts.States().size()
        ));
        
        
        for(Transition transition : lts.Transitions())
        {
            ConcatLine(String.format(
                    "(%d, %s, %d)",
                    IndexFor(transition.GetOrign()),
                    LabelFor(transition.GetLabel()),
                    IndexFor(transition.GetDestination())));
        }
        
        
        return output.toString();
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
    
    private void ConcatLine(String line)
    {
        output =  output.append(line).append("\n");
    }
}
