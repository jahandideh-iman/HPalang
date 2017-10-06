/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.SpaceEx.Convertor.QueueCreationUtilities;

import HPalang.SpaceEx.Convertor.ActorModelData;
import HPalang.SpaceEx.Core.Flow;
import HPalang.SpaceEx.Core.HybridLabel;
import HPalang.SpaceEx.Core.Invarient;
import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author Iman Jahandideh
 */
public class UrgentQueueLocation extends QueueLocation
{

    public UrgentQueueLocation(Queue<String> content, ActorModelData actorData)
    {
        super(String.join("_", content) + "_1", content, actorData);
        loc.AddFlow(new Flow(actorData.GetUrgentFlow()));
        loc.AddInvarient(new Invarient(actorData.GetUrgentInvarient()));
    }

    public UrgentQueueLocation(String name, ActorModelData actorData)
    {
        super(name, new LinkedList<>(), actorData);
        loc.AddFlow(new Flow(actorData.GetUrgentFlow()));
        loc.AddInvarient(new Invarient(actorData.GetUrgentInvarient()));
    }

    @Override
    public void ProcessInLabel(HybridLabel label)
    {
        label.AddAssignment(actorData.GetUrgentReset());
    }

    @Override
    public void ProcessOutLabel(HybridLabel label)
    {
        label.AddGuard(actorData.GetUrgentGuard());
    }
}
