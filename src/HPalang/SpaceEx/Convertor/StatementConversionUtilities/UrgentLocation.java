/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.SpaceEx.Convertor.StatementConversionUtilities;

import HPalang.SpaceEx.Convertor.ActorModelData;
import HPalang.SpaceEx.Core.Flow;
import HPalang.SpaceEx.Core.HybridLabel;
import HPalang.SpaceEx.Core.Invarient;
import HPalang.SpaceEx.Core.Location;

/**
 *
 * @author Iman Jahandideh
 */
public abstract class UrgentLocation extends StatementLocation
{

    public UrgentLocation(String name, ActorModelData actorData)
    {
        super(actorData);

        this.loc = new Location(name);
        loc.AddInvarient(new Invarient(actorData.GetUrgentInvarient()));
        loc.AddFlow(new Flow(actorData.GetUrgentFlow()));
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
