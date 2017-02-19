/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Core;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/**
 *
 * @author Iman Jahandideh
 */
public class ValuationContainer extends EqualitableAndClonable<ValuationContainer> implements Iterable<Entry<DiscreteVariable,Integer>>
{
    private Map<DiscreteVariable,Integer> values = new HashMap<>();
    
    public void Set(DiscreteVariable var, int value)
    {
        values.put(var, value);
    }
    
    public void Add(DiscreteVariable var)
    {
        values.put(var, 0);
    }
    
    public boolean Has(DiscreteVariable var)
    {
        return values.containsKey(var);
    }
    
    public int Get(DiscreteVariable var)
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
        
        copy.values = new HashMap<>(values);
        
        return copy;
    }

    @Override
    public Iterator<Entry<DiscreteVariable, Integer>> iterator()
    {
        return values.entrySet().iterator();
    }
}
