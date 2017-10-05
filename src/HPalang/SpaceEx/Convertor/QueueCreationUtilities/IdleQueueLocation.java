/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.SpaceEx.Convertor.QueueCreationUtilities;

import HPalang.SpaceEx.Convertor.ActorModelData;
import java.util.LinkedList;

/**
 *
 * @author Iman Jahandideh
 */
public class IdleQueueLocation extends QueueLocation
{

    public IdleQueueLocation(String name, ActorModelData actorData)
    {
        super(name, new LinkedList<String>(), actorData);
    }

    @Override
    public void ProcessInTransition(QueueTransition transition)
    {
    }

    @Override
    public void PrcoessOutTransition(QueueTransition transition)
    {
    }
}
