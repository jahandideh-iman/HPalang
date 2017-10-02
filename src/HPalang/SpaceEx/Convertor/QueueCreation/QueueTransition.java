/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.SpaceEx.Convertor.QueueCreation;

import HPalang.SpaceEx.Core.BaseComponent;
import HPalang.SpaceEx.Core.HybridLabel;
import HPalang.SpaceEx.Core.HybridTransition;

/**
 *
 * @author Iman Jahandideh
 */
public class QueueTransition
{

    public final QueueLocation origin;
    public final HybridLabel label;
    public final QueueLocation destination;

    public QueueTransition(QueueLocation origin, HybridLabel label, QueueLocation destination)
    {
        this.origin = origin;
        this.label = label;
        this.destination = destination;
    }

    public void Process(BaseComponent comp)
    {
        origin.PrcoessOutTransition(this);
        destination.ProcessInTransition(this);
        HybridTransition firstTrans = new HybridTransition(origin.GetLoc(), label, destination.GetLoc());
        comp.AddTransition(firstTrans);
    }

}
