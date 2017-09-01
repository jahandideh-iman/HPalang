/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Mocks;

import HPalang.Core.ValuationContainer;
import HPalang.Core.Variable;
import HPalang.Core.Variables.IntegerVariable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Iman Jahandideh
 */
public class ValuationContainerMock implements ValuationContainer
{

    public  Set<Variable> removedVariables = new HashSet<>();
    
    private final Map<Variable, Integer> values = new HashMap<>();
    
    @Override
    public void Add(Variable var)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void Add(Map<IntegerVariable, Integer> values)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void Add(ValuationContainer other)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void Set(Variable var, int value)
    {
        values.put(var, value);
    }

    @Override
    public int ValueFor(Variable var)
    {
        return values.get(var);
    }

    @Override
    public boolean Has(Variable var)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void Remove(Variable var)
    {
        removedVariables.add(var);
    }

    @Override
    public ValuationContainer DeepCopy()
    {
        return this;
    }

    @Override
    public Iterator<Map.Entry<IntegerVariable, Integer>> iterator()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
