/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.SpaceEx.Convertor.QueueCreation;

import HPalang.SpaceEx.Core.HybridLabel;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Iman Jahandideh
 */
public class QueueTransitionBuilder
{
    private QueueLocation origin;
    private QueueLocation destination;
    
    private List<String> guards = new LinkedList<>();
    private List<String> assignments = new LinkedList<>();
    private String syncLabel = "";
    
    
    QueueTransitionBuilder SetOrigin(QueueLocation origin)
    {
        this.origin = origin;
        return this;
    }
    
    QueueTransitionBuilder SetDestination(QueueLocation destination)
    {
        this.destination = destination;
        return this;
    }
    
    QueueTransitionBuilder AddGuard(String guard)
    {
        guards.add(guard);
        return this;
    }
    
    QueueTransitionBuilder AddAssignment(String assignent)
    {
        assignments.add(assignent);
        return this;
    }
        
    QueueTransitionBuilder SetSynclabel(String syncLabel)
    {
        this.syncLabel = syncLabel;
        return this;
    }
    
    
    public QueueTransition Build()
    {
        HybridLabel label = new HybridLabel();
        
        guards.forEach(g -> label.AddGuard(g));
        assignments.forEach(a -> label.AddAssignment(a));
        label.SetSyncLabel(syncLabel);
        
        QueueTransition tran = new QueueTransition(origin, label, destination);
        
        
        return tran;
    }

}
