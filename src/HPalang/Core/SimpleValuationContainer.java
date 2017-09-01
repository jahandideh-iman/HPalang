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
import java.util.function.Consumer;

/**
 *
 * @author Iman Jahandideh
 */
public class SimpleValuationContainer extends Equalitable<SimpleValuationContainer> implements ValuationContainer
{
    private final Map<IntegerVariable,Integer> values = new HashMap<>();
    
    public void Set(Variable var, int value)
    {
        values.put((IntegerVariable)var, value);
    }
    
    @Override
    public void Add(Variable var)
    {
        if(var instanceof IntegerVariable)
            values.put((IntegerVariable)var, 0);
    }
    
    @Override
    public void Add(ValuationContainer valuation)
    {
        valuation.forEach(e -> values.put(e.getKey(), e.getValue()));
    }
    
    public void Add(Map<IntegerVariable,Integer> values)
    {
        values.putAll(values);
    }
    
    public boolean Has(IntegerVariable var)
    {
        return values.containsKey(var);
    }
    
    public int ValueFor(Variable var)
    {
        return values.get(var);
    }

    @Override
    protected boolean InternalEquals(SimpleValuationContainer other)
    {
        return values.equals(other.values);
    }

    @Override
    protected int InternalHashCode()
    {
        return values.hashCode();
    }

    @Override
    public SimpleValuationContainer DeepCopy()
    {
        SimpleValuationContainer copy = new SimpleValuationContainer();
        
        copy.Add(this);
        
        return copy;
    }

    @Override
    public Iterator<Entry<IntegerVariable, Integer>> iterator()
    {
        return values.entrySet().iterator();
    }

    @Override
    public boolean Has(Variable var)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void Remove(Variable var)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
