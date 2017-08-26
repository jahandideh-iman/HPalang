/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Core;

import HPalang.Core.Variables.IntegerVariable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/**
 *
 * @author Iman Jahandideh
 */
public class ValuationContainer extends EqualitableAndClonable<ValuationContainer> implements Iterable<Entry<IntegerVariable,Integer>>
{
    private final Map<IntegerVariable,Integer> values = new HashMap<>();
    
    public void Set(IntegerVariable var, int value)
    {
        values.put(var, value);
    }
    
    public void Add(Variable var)
    {
        if(var instanceof IntegerVariable)
            values.put((IntegerVariable)var, 0);
    }
    
    public void Add(ValuationContainer valuation)
    {
        values.putAll(valuation.values);
    }
    
    public void Add(Map<IntegerVariable,Integer> values)
    {
        values.putAll(values);
    }
    
    public boolean Has(IntegerVariable var)
    {
        return values.containsKey(var);
    }
    
    public int Get(IntegerVariable var)
    {
        return values.get(var);
    }

    @Override
    protected boolean InternalEquals(ValuationContainer other)
    {
        return values.equals(other.values);
    }

    @Override
    protected int InternalHashCode()
    {
        return values.hashCode();
    }

    @Override
    public ValuationContainer DeepCopy()
    {
        ValuationContainer copy = new ValuationContainer();
        
        copy.Add(this);
        
        return copy;
    }

    @Override
    public Iterator<Entry<IntegerVariable, Integer>> iterator()
    {
        return values.entrySet().iterator();
    }
}
