/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.SpaceEx.Core;

import HPalang.Core.Equalitable;

/**
 *
 * @author Iman Jahandideh
 */
public abstract class Parameter extends Equalitable<Parameter> implements Visitable
{
    private String name;
    private boolean isLocal;
    
    public Parameter(String name, boolean isLocal)
    {
        this.name = name;
        this.isLocal = isLocal;
    }
    
    public String GetName()
    {
        return name;
    }
    
    public Boolean IsLocal()
    {
        return isLocal;
    }
    
    @Override
    protected boolean InternalEquals(Parameter other)
    {
        return other.isLocal == isLocal && other.name.equals(name);
    }

    @Override
    protected int InternalHashCode()
    {
        return name.hashCode();
    }
}
