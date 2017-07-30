/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.RunTimeStates;

import HPalang.Core.DiscreteVariable;
import HPalang.Core.ValuationContainer;
import HPalang.LTSGeneration.SimpleState;
import java.util.Map;

/**
 *
 * @author Iman Jahandideh
 */
public class ValuationState extends SimpleState<ValuationState>
{
    private final ValuationContainer valuation = new ValuationContainer();
    
    public ValuationState()
    {
    }
    
    public ValuationState(Map<DiscreteVariable, Integer> initialValuations)
    {
        valuation.Add(initialValuations);
    }
    
    public ValuationContainer Valuation()
    {
        return valuation;
    }
    
    @Override
    protected boolean DataEquals(ValuationState other)
    {
        return valuation.equals(other.valuation);
    }

    @Override
    protected ValuationState NewInstance()
    {
        return new ValuationState();
    }

    @Override
    protected void CloneData(ValuationState copy)
    {
        copy.valuation.Add(valuation);
    }

    @Override
    protected int InternalHashCode()
    {
        return valuation.hashCode();
    }
    
}
