/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.SpaceEx.Core;

import HPalang.Core.Equalitable;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Iman Jahandideh
 */
public class SpaceExModel extends Equalitable<SpaceExModel> implements Visitable
{

    List<Component> components = new LinkedList<>();
    
    public void AddComponent(Component component)
    {
        components.add(component);
    }
    
    public List<Component> GetComponents()
    {
        return components;
    }
    
    @Override
    public void Accept(ModelVisitor visitor)
    {
        visitor.Visit(this);
    }

    @Override
    protected boolean InternalEquals(SpaceExModel other)
    {
        return other != null;
    }

    @Override
    protected int InternalHashCode()
    {
        return 0;
    }
    
}
