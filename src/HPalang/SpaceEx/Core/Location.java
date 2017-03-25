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
public class Location extends Node implements Visitable
{
    private String id;
    private String name;
    private List<Flow> flows = new LinkedList<>();
    private List<Invarient> invarients = new LinkedList<>();
    
    public Location(String name)
    {
        this.name = name;
    }
    
    public void SetId(String id)
    {
        this.id = id;
    }
    
    public void AddFlow(Flow flow)
    {
        flows.add(flow);
    }
    
    public void AddInvarient(Invarient invarient)
    {
        invarients.add(invarient);
    }
    
    public Collection<Flow> GetFlows()
    {
        return flows;
    }
    
    public Collection<Invarient> GetInvarients()
    {
        return invarients;
    }
    
    public String GetId()
    {
        return id;
    }
    
    public String GetName()
    {
        return name;
    }

    @Override
    public void Accept(Visitor visitor)
    {
        visitor.Visit(this);
    }
}
