/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.SOSRules;

import HPalang.LTSGeneration.Labels.NetworkLabel;
import HPalang.LTSGeneration.Labels.SoftwareLabel;
import HPalang.LTSGeneration.Transition;
import java.util.Collection;

/**
 *
 * @author Iman Jahandideh
 */
public class Utilities
{
    public static boolean HasSoftwareActions(Collection<Transition> transitions)
    {
        return ! NoSoftwareActions(transitions);
    }
    public static boolean NoSoftwareActions(Collection<Transition> transitions)
    {
        for(Transition tr : transitions)
            if(tr.GetLabel() instanceof SoftwareLabel)
                return false;
        return true;
    }

    public static boolean HasNetworkActions(Collection<Transition> transitions)
    {
        return ! NoNetworkActions(transitions);
    }
        
    public static boolean NoNetworkActions(Collection<Transition> transitions)
    {
        for(Transition tr : transitions)
            if(tr.GetLabel() instanceof NetworkLabel)
                return false;
        return true;
    }
}
