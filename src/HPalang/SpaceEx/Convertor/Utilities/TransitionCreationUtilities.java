/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.SpaceEx.Convertor.Utilities;

import HPalang.SpaceEx.Convertor.QueueCreationUtilities.QueueLocation;
import HPalang.SpaceEx.Core.HybridLabel;

/**
 *
 * @author Iman Jahandideh
 */
public class TransitionCreationUtilities
{
        
    public static ProcessableTransition CreateTransition(QueueLocation origin , QueueLocation destination)
    {
        HybridLabel label = new HybridLabel();
        return new ProcessableTransition(origin, label, destination, false);
    }
    
    public static ProcessableTransition CreateLabeledTransition(QueueLocation origin, String syncLabel, QueueLocation destination)
    {
        HybridLabel label = new HybridLabel();
        label.SetSyncLabel(syncLabel);
        return new ProcessableTransition(origin, label, destination,false);
    }
    
    public static ProcessableTransition CreateGuardedTransition(QueueLocation origin, String guard, QueueLocation destination)
    {
        HybridLabel label = new HybridLabel();
        label.AddGuard(guard);
        return new ProcessableTransition(origin, label, destination,false);
    } 
    
    public static ProcessableTransition CreateGuardedAndLabledTransition(QueueLocation origin, String guard, String syncLabel, QueueLocation destination)
    {
        HybridLabel label = new HybridLabel();
        label.AddGuard(guard);
        label.SetSyncLabel(syncLabel);
        return new ProcessableTransition(origin, label, destination, false);
    }
}
