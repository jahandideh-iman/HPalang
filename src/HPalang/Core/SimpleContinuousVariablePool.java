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
    private final Set<RealVariable> avaiableVariables = new HashSet<>();
    private final Set<RealVariable> origianlVariables = new HashSet<>();
    private final String prefix;
    
    public SimpleContinuousVariablePool(int size)
    {
        this("reserved_",size);
    }
    
    public SimpleContinuousVariablePool(String prefix,int size)
    {
        this.prefix = prefix;
        
        for(int i = 0; i< size; i++)
            origianlVariables.add(new RealVariable(prefix + i ));
        
        avaiableVariables.addAll(origianlVariables);
    }
    
    public SimpleContinuousVariablePool(SimpleContinuousVariablePool other)
    {
        this.prefix = other.prefix;
        origianlVariables.addAll(other.origianlVariables);
        avaiableVariables.addAll(other.avaiableVariables);
    }
    
    @Override
    public RealVariable Acquire()
    {
        if(!HasAnyAvailableVariable())
            throw new RuntimeException("The is no real variable to acquire");
        else
        {
            RealVariable var = avaiableVariables.iterator().next();
            avaiableVariables.remove(var);
            return var;
        }
    }
        
    @Override
    public void Release(RealVariable variable)
    {
        assert (origianlVariables.contains(variable) && !avaiableVariables.contains(variable));
        avaiableVariables.add(variable);
    }
    
    @Override
    public boolean Has(RealVariable variable)
    {
        return avaiableVariables.contains(variable);
    }
    public int Size()
    {
        return avaiableVariables.size();
    }
    
    @Override
    protected boolean InternalEquals(SimpleContinuousVariablePool other)
    {
        return avaiableVariables.equals(other.avaiableVariables) &&
                origianlVariables.equals(other.origianlVariables);
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
        return avaiableVariables.isEmpty() == false;
    }

    @Override
    public boolean HasAvailableVariable(int number)
    {
        return avaiableVariables.size() >= number;
    }
}
