/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.SpaceEx.Convertor.StatementConversionUtilities;

import HPalang.SpaceEx.Convertor.ActorModelData;
import HPalang.SpaceEx.Core.HybridLabel;

/**
 *
 * @author Iman Jahandideh
 */
public class EndLocation extends UrgentLocation
{

    public EndLocation(String name, ActorModelData actorData)
    {
        super(name, actorData);
    }

    @Override
    public void ProcessOutLabel(HybridLabel label)
    {
        super.ProcessOutLabel(label);
        label.AddAssignment(actorData.GetLockReleaseReset());
    }
}
