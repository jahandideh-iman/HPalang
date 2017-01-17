/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.RunTimeStates;

import HPalang.Core.DiscreteVariable;
import HPalang.Core.Equalitable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import javafx.util.Pair;

/**
 *
 * @author Iman Jahandideh
 */
public class ValuationMap extends EqualitableAndClonable<ValuationMap> implements Iterable<Entry<DiscreteVariable,Integer>>
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
    protected boolean InternalEquals(ValuationMap other)
    {
        return values.equals(other.values);
    }

    @Override
    protected int InternalHashCode()
    {
        return values.hashCode();
    }

    @Override
    public ValuationMap DeepCopy()
    {
        ValuationMap copy = new ValuationMap();
        
        copy.values = new HashMap<>(values);
        
        return copy;
    }

    @Override
    public Iterator<Entry<DiscreteVariable, Integer>> iterator()
    {
        return values.entrySet().iterator();
    }
}
