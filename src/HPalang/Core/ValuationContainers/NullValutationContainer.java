/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Core.ValuationContainers;

import HPalang.Core.Equalitable;
import HPalang.Core.ShouldNotBeUsedException;
import HPalang.Core.ValuationContainer;
import HPalang.Core.Variable;
import HPalang.Core.Variables.IntegerVariable;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;

/**
 *
 * @author Iman Jahandideh
 */
public class NullValutationContainer extends Equalitable<NullValutationContainer> implements ValuationContainer
{   
    @Override
    protected boolean InternalEquals(NullValutationContainer other)
    {
        return true;
    }

    @Override
    protected int InternalHashCode()
    {
        return 0;
    }

    @Override
    public void Add(Variable var)
    {
        throw new ShouldNotBeUsedException();
    }

    @Override
    public void Add(Map<IntegerVariable, Integer> values)
    {
        throw new ShouldNotBeUsedException();
    }

    @Override
    public void Add(ValuationContainer other)
    {
        throw new ShouldNotBeUsedException();
    }

    @Override
    public void Set(Variable var, int value)
    {
        throw new ShouldNotBeUsedException();
    }

    @Override
    public int ValueFor(Variable var)
    {
        throw new ShouldNotBeUsedException();
    }

    @Override
    public boolean Has(Variable var)
    {
        return false;
    }

    @Override
    public void Remove(Variable var)
    {
        throw new ShouldNotBeUsedException();
    }

    @Override
    public ValuationContainer DeepCopy()
    {
        return this;
    }
    
    @Override
    public Iterator<Map.Entry<IntegerVariable, Integer>> iterator()
    {
        return Collections.emptyIterator();
    }
    
}
