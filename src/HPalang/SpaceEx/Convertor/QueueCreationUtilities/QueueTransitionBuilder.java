/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.SpaceEx.Convertor.QueueCreationUtilities;

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
    
    
    public QueueTransitionBuilder SetOrigin(QueueLocation origin)
    {
        this.origin = origin;
        return this;
    }
    
    public QueueTransitionBuilder SetDestination(QueueLocation destination)
    {
        this.destination = destination;
        return this;
    }
    
    public QueueTransitionBuilder AddGuard(String guard)
    {
        guards.add(guard);
        return this;
    }
    
    public QueueTransitionBuilder AddAssignment(String assignent)
    {
        assignments.add(assignent);
        return this;
    }
    
    public QueueTransitionBuilder AddAssignments(List<String> assignments)
    {
        this.assignments.addAll(assignments);
        return this;
    }
        
    public QueueTransitionBuilder SetSynclabel(String syncLabel)
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
