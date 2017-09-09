/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Core;

import HPalang.Core.Variables.RealVariable;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Iman Jahandideh
 */
public class SimpleContinuousVariablePool extends Equalitable<SimpleContinuousVariablePool> implements RealVariablePool
{
    private final Set<RealVariable> variables = new HashSet<>();
    private final String prefix;
    
    public SimpleContinuousVariablePool(int size)
    {
        this("reserved_",size);
    }
    
    public SimpleContinuousVariablePool(String prefix,int size)
    {
        this.prefix = prefix;
        
        for(int i = 0; i< size; i++)
            variables.add(new RealVariable("timer_" + i ));
    }
    
    public SimpleContinuousVariablePool(SimpleContinuousVariablePool other)
    {
        this.prefix = other.prefix;
        variables.addAll(other.variables);
    }
    
    @Override
    public RealVariable Acquire()
    {
        if(!HasAnyAvailableVariable())
            throw new RuntimeException("The is no real variable to acquire");
        else
        {
            RealVariable var = variables.iterator().next();
            variables.remove(var);
            return var;
        }
    }
        
    @Override
    public void Release(RealVariable variable)
    {
        variables.add(variable);
    }
    
    @Override
    public boolean Has(RealVariable variable)
    {
        return variables.contains(variable);
    }
    public int Size()
    {
        return variables.size();
    }
    
    @Override
    protected boolean InternalEquals(SimpleContinuousVariablePool other)
    {
        return variables.equals(other.variables);
    }

    @Override
    protected int InternalHashCode()
    {
        return 0;
    }

    @Override
    public RealVariablePool DeepCopy()
    {
        return new SimpleContinuousVariablePool(this);
    }

    @Override
    public boolean HasAnyAvailableVariable()
    {
        return variables.isEmpty() == false;
    }

    @Override
    public boolean HasAvailableVariable(int number)
    {
        return variables.size() >= number;
    }
}
