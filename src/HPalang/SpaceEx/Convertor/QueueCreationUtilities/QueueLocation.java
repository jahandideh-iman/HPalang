/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.SpaceEx.Convertor.QueueCreationUtilities;

import HPalang.SpaceEx.Convertor.ActorModelData;
import HPalang.SpaceEx.Convertor.Utilities.ProcessableLocation;
import HPalang.SpaceEx.Core.Location;
import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author Iman Jahandideh
 */
public abstract class QueueLocation extends ProcessableLocation
{
    private final Queue<String> content = new LinkedList<>();

    public QueueLocation(String name, Queue<String> content, ActorModelData actorData)
    {
        super(name,actorData);
        this.content.addAll(content);
    }

    public Queue<String> GetContent()
    {
        return content;
    }

}

