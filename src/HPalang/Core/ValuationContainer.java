/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Core;

import HPalang.Core.Variables.IntegerVariable;
import java.util.Map;

/**
 *
 * @author Iman Jahandideh
 */
public interface ValuationContainer extends DeepClonable<ValuationContainer>, Iterable<Map.Entry<IntegerVariable,Integer>>
{
    public void Add(Variable var);
    public void Add(Map<IntegerVariable,Integer> values);
    public void Add(ValuationContainer other);
    public void Set(Variable var, int value);
    public int ValueFor(Variable var);
    public boolean Has(Variable var);
    public void Remove(Variable var);
}
