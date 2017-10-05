/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.SpaceEx.Convertor.StatementConversionUtilities;

import HPalang.SpaceEx.Convertor.ActorModelData;
import HPalang.SpaceEx.Core.HybridLabel;
import HPalang.SpaceEx.Core.Location;

/**
 *
 * @author Iman Jahandideh
 */
public abstract class StatementLocation
{
    protected Location loc;
    protected ActorModelData actorData;
    
    public StatementLocation(ActorModelData actorData)
    {
        this.actorData = actorData;
    }
    
    public Location GetLoc()
    {
        return loc;
    }

    public abstract void ProcessInLabel(HybridLabel label);
    public abstract void ProcessOutLabel(HybridLabel label);
}
