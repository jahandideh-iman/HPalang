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
public class NetworkComponent extends Component
{
    
    private List<ComponentInstance> instances = new LinkedList<>();
    
    public NetworkComponent(String id)
    {
        super(id);
    }
    
    public void AddInstance(ComponentInstance instance)
    {
        instances.add(instance);
    }
    
    public Collection<ComponentInstance> GetInstances()
    {
        return instances;
    }

    @Override
    public void Accept(ModelVisitor visitor)
    {
        visitor.Visit(this);
    }
}
