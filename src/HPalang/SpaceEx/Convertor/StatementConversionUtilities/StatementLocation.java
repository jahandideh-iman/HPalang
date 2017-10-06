/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.SpaceEx.Convertor.StatementConversionUtilities;

import HPalang.SpaceEx.Convertor.ActorModelData;
import HPalang.SpaceEx.Convertor.Utilities.ProcessableLocation;
import HPalang.SpaceEx.Core.HybridLabel;
import HPalang.SpaceEx.Core.Location;

/**
 *
 * @author Iman Jahandideh
 */
public abstract class StatementLocation extends ProcessableLocation
{
    
    public StatementLocation(String name, ActorModelData actorData)
    {
        super(name, actorData);
    }
}
