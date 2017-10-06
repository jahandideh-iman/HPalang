/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.SpaceEx.Convertor.Utilities;

import HPalang.SpaceEx.Convertor.ActorModelData;
import HPalang.SpaceEx.Core.HybridLabel;
import HPalang.SpaceEx.Core.Location;

/**
 *
 * @author Iman Jahandideh
 */
public abstract class ProcessableLocation
{
    protected Location loc;
    protected ActorModelData actorData;

    public ProcessableLocation(String name, ActorModelData actorData)
    {
        this.actorData = actorData;
        this.loc = new Location(name);
    }

    public Location GetLoc()
    {
        return loc;
    }

    public abstract void ProcessInLabel(HybridLabel label);

    public abstract void ProcessOutLabel(HybridLabel label);
}
