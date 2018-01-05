/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.RunTimeStates;

import HPalang.Core.ValuationContainers.SimpleValuationContainer;
import HPalang.Core.ValuationContainer;
import HPalang.Core.Variables.IntegerVariable;
import HPalang.LTSGeneration.SimpleState;
import java.util.Map;

/**
 *
 * @author Iman Jahandideh
 */
public class ValuationState extends SimpleState<ValuationState>
{
    private ValuationContainer  valuation;
    
    public ValuationState()
    {
        
    }
    
    public ValuationState(ValuationContainer valuation)
    {
        this.valuation = valuation;
    }
        
    public ValuationState(Map<IntegerVariable, Integer> initialValuations)
    {
        if(valuation == null)
            valuation = new SimpleValuationContainer();
        valuation.Add(initialValuations);
    }
    
    // NOTE: For testing only.
    public void SetValuation(ValuationContainer newValuation)
    {
        this.valuation = newValuation;
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
        copy.valuation = this.valuation.DeepCopy();
    }

    @Override
    protected int InternalHashCode()
    {
        return valuation.hashCode();
    }
    
}
