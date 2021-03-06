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

// TODO: Don't use enum for type. Refactor this.
public abstract class Variable extends Equalitable<Variable> 
{
    public enum Type { real, integer, floatingPoint};
    
    private final String name;
    private final Type type;
    
    @Deprecated
    public Variable(String name)
    {
        this(name, Type.integer);
    }

    public Variable(String name, Type type)
    {
        this.name = name;
        this.type = type;
    }
    
    public abstract void Visit(VariableVisitor visitor);
    
    public Type Type()
    {
        return type;
    }
    
    public String Name()
    {
        return name;
    }
    
    @Override
    protected boolean InternalEquals(Variable other)
    {
        return this.name.equals(other.name) &&
                this.type.equals(other.type);
    }

    @Override
    protected int InternalHashCode()
    {
        return name.hashCode() + type.hashCode();
    }
        
    @Override
    public String toString()
    {
        return name;
    }
    
}
