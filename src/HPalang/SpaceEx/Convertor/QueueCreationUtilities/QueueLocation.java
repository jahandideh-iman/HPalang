/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.SpaceEx.Convertor.QueueCreationUtilities;

import HPalang.SpaceEx.Convertor.ActorModelData;
import HPalang.SpaceEx.Core.Location;
import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author Iman Jahandideh
 */
public abstract class QueueLocation
{
    protected Location loc;
    private final Queue<String> content = new LinkedList<>();
    protected ActorModelData actorData;

    public QueueLocation(String name, Queue<String> content, ActorModelData actorData)
    {
        this.content.addAll(content);
        loc = new Location(name);
        this.actorData = actorData;
    }

    public Location GetLoc()
    {
        return loc;
    }
    public Queue<String> GetContent()
    {
        return content;
    }

    public abstract void ProcessInTransition(QueueTransition transition);
    public abstract void PrcoessOutTransition(QueueTransition transition);
}

