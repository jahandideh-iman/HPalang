/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Convertors;

import HPalang.Core.DiscreteExpressions.BinaryExpression;
import HPalang.LTSGeneration.Label;
import HPalang.LTSGeneration.Labels.Guard;
import HPalang.LTSGeneration.Labels.Reset;
import java.util.Set;

/**
 *
 * @author Iman Jahandideh
 */
public class StringConversionUtilities
{
    static public String StringFor(Label label)
    {
        if(label.IsGuarded())
            return String.format(
                    "%s, %s", 
                    StringFor(label.Guard()), 
                    StringFor(label.Resets()));
        else
            return StringFor(label.Resets());
    }
    
    static public String StringFor(Set<Reset> resets)
    {
        String output = "";
        for(Reset reset : resets)
            output = String.join(",", output, reset.toString());
        
        return output;
    }
    static public String StringFor(Guard guard)
    {
        return guard.toString();
    }
    
    static public String StringFor(BinaryExpression expression)
    {
        return expression.toString();
    }
}
