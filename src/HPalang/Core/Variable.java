/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Core;

import java.util.Objects;

/**
 *
 * @author Iman Jahandideh
 */
public class Variable extends Equalitable<Variable> 
{
    private final String name;
    
    public Variable(String name)
    {
        this.name = name;
    }
    
    public String Name()
    {
        return name;
    }
    
    @Override
    protected boolean InternalEquals(Variable other)
    {
        return this.name.equals(other.name);
    }

    @Override
    protected int InternalHashCode()
    {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.name);
        return hash;
    }
    
    @Override
    public String toString()
    {
        return name;
    }
}
