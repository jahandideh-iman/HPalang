/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.SpaceEx.Convertor.Utilities;

import HPalang.SpaceEx.Convertor.QueueCreationUtilities.QueueLocation;
import HPalang.SpaceEx.Core.BaseComponent;
import HPalang.SpaceEx.Core.HybridLabel;
import HPalang.SpaceEx.Core.HybridTransition;

/**
 *
 * @author Iman Jahandideh
 */
public class ProcessableTransition
{
    public final ProcessableLocation origin;
    public final HybridLabel label;
    public final ProcessableLocation destination;

    public ProcessableTransition(ProcessableLocation origin,ProcessableLocation destination)
    {
        this(origin, new HybridLabel(), destination);
    }
        
    public ProcessableTransition(ProcessableLocation origin, HybridLabel label, ProcessableLocation destination)
    {
        this.origin = origin;
        this.label = label;
        this.destination = destination;
    }

    public void Process(BaseComponent comp)
    {
        origin.ProcessOutLabel(this.label);
        destination.ProcessInLabel(this.label);
        HybridTransition firstTrans = new HybridTransition(origin.GetLoc(), label, destination.GetLoc());
        comp.AddTransition(firstTrans);
    }
}
