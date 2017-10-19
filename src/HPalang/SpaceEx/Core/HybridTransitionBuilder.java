/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.SpaceEx.Core;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Iman Jahandideh
 */
public class HybridTransitionBuilder
{
    private Location origin;
    private Location destination;

    private List<String> guards = new LinkedList<>();
    private List<String> assignments = new LinkedList<>();
    private String syncLabel = "";
    private boolean isASAP = false;

    public HybridTransitionBuilder SetOrigin(Location origin)
    {
        this.origin = origin;
        return this;
    }

    public HybridTransitionBuilder SetDestination(Location destination)
    {
        this.destination = destination;
        return this;
    }

    public HybridTransitionBuilder AddGuard(String guard)
    {
        guards.add(guard);
        return this;
    }

    public HybridTransitionBuilder AddAssignment(String assignent)
    {
        assignments.add(assignent);
        return this;
    }

    public HybridTransitionBuilder SetSynclabel(String syncLabel)
    {
        this.syncLabel = syncLabel;
        return this;
    }
    
    public HybridTransitionBuilder SetASAP(boolean isASAP)
    {
        this.isASAP = isASAP;
        return this;
    }

    public HybridTransition Build()
    {
        HybridLabel label = new HybridLabel();

        guards.forEach(g -> label.AddGuard(g));
        assignments.forEach(a -> label.AddAssignment(a));
        label.SetSyncLabel(syncLabel);

        HybridTransition tran = new HybridTransition(origin, label, destination, isASAP);

        return tran;
    }

}
