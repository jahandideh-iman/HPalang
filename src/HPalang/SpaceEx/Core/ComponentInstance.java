/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.SpaceEx.Core;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Iman Jahandideh
 */
public class ComponentInstance implements Visitable
{
    private String name;
    private Component type;
    
    private Map<String,String> bindings = new HashMap<>();
    
    
    public ComponentInstance(String name, Component type)
    {
        this.name = name;
        this.type = type;
    }
    
    public void SetBinding(String formalParam, String actualParam)
    {
        bindings.put(formalParam, actualParam);
    }
    
    public Collection<String> GetBindedFromalParams()
    {
       return bindings.keySet();
    }
    
    public String GetBinding(String formalParam)
    {
        return bindings.get(formalParam);
    }
    
    public Component GetType()
    {
        return type;
    }
    
    public String GetName()
    {
        return name;
    }

    @Override
    public void Accept(ModelVisitor visitor)
    {
        visitor.Visit(this);
    }
}
