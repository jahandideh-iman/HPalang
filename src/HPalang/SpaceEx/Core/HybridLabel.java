/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.SpaceEx.Core;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Iman Jahandideh
 */
public class HybridLabel extends Label
{
    private String syncLabel = "";
    private final List<String> guards = new LinkedList<>();
    private final List<String> assignments = new LinkedList<>();
    

    
    public String GetSyncLabel()
    {
        return syncLabel;
    }

    public Collection<String> GetGuards()
    {
        return guards;
    }

    public Collection<String> GetAssignments()
    {
        return assignments;
    }
    
    public HybridLabel SetSyncLabel(String label)
    {
        this.syncLabel = label;
        return this;
    }
    
    public HybridLabel AddGuard(String guard)
    {
        guards.add(guard);
        return this;
    }
    
    public HybridLabel AddAssignment(String assignment)
    {
        assignments.add(assignment);
        return this;
    }
}
