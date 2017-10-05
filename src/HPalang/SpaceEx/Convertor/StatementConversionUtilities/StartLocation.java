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
public class StartLocation extends UrgentLocation
{

    public StartLocation(String name, ActorModelData actorData)
    {
        super(name, actorData);
    }

    @Override
    public void ProcessInLabel(HybridLabel label)
    {
        super.ProcessInLabel(label);
        label.AddGuard(actorData.GetLockGainGuard());
        label.AddAssignment(actorData.GetLockGainReset());
    }

}
