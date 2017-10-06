/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.SpaceEx.Convertor.Utilities;

import HPalang.SpaceEx.Core.HybridLabel;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Iman Jahandideh
 */
public class ProcessableTransitionBuilder
{
    private ProcessableLocation origin;
    private ProcessableLocation destination;
    
    private final List<String> guards = new LinkedList<>();
    private final List<String> assignments = new LinkedList<>();
    private String syncLabel = "";
    
    
    public ProcessableTransitionBuilder SetOrigin(ProcessableLocation origin)
    {
        this.origin = origin;
        return this;
    }
    
    public ProcessableTransitionBuilder SetDestination(ProcessableLocation destination)
    {
        this.destination = destination;
        return this;
    }
    
    public ProcessableTransitionBuilder AddGuard(String guard)
    {
        guards.add(guard);
        return this;
    }
    
    public ProcessableTransitionBuilder AddAssignment(String assignent)
    {
        assignments.add(assignent);
        return this;
    }
    
    public ProcessableTransitionBuilder AddAssignments(List<String> assignments)
    {
        this.assignments.addAll(assignments);
        return this;
    }
        
    public ProcessableTransitionBuilder SetSynclabel(String syncLabel)
    {
        this.syncLabel = syncLabel;
        return this;
    }
    
    
    public ProcessableTransition Build()
    {
        HybridLabel label = new HybridLabel();
        
        guards.forEach(g -> label.AddGuard(g));
        assignments.forEach(a -> label.AddAssignment(a));
        label.SetSyncLabel(syncLabel);
        
        ProcessableTransition tran = new ProcessableTransition(origin, label, destination);
        
        
        return tran;
    }



}
