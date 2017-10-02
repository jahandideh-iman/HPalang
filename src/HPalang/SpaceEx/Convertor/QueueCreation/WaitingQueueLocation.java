/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.SpaceEx.Convertor.QueueCreation;

import HPalang.SpaceEx.Convertor.ActorModelData;
import HPalang.SpaceEx.Core.Invarient;
import java.util.Queue;

/**
 *
 * @author Iman Jahandideh
 */
class WaitingQueueLocation extends QueueLocation
{
    public WaitingQueueLocation(Queue<String> content, ActorModelData actorData)
    {
        super(String.join("_", content) + "_2", content, actorData);
        loc.AddInvarient(new Invarient(actorData.GetBusyInvarient()));
    }

    @Override
    public void ProcessInTransition(QueueTransition transition)
    {
    }

    @Override
    public void PrcoessOutTransition(QueueTransition transition)
    {
        transition.label.AddGuard(actorData.GetIsNotBusyGuard());
    }
}
