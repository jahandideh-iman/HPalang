/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.SpaceEx.Convertor;

import HPalang.SpaceEx.Convertor.QueueCreationUtilities.QueueLocation;
import HPalang.SpaceEx.Core.BaseComponent;
import HPalang.SpaceEx.Core.Location;

/**
 *
 * @author Iman Jahandideh
 */
public class CANCreator
{
    private BaseComponent canComponent;
    
    private Location initialLocation_0 ;
    
    public void Create()
    {
        canComponent = new BaseComponent("CAN");
        initialLocation_0 = new Location("idle");
        
        AddLocation(initialLocation_0);
    }
    
    private void AddLocation(Location location)
    {
        canComponent.AddLocation(location);
    }
}
