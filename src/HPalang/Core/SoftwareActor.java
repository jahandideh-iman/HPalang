/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Core;

import HPalang.Core.Variables.IntegerVariable;
import HPalang.LTSGeneration.RunTimeStates.SoftwareActorState;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Iman Jahandideh
 */
public class SoftwareActor extends Actor<SoftwareActorType>
{
    private final Map<IntegerVariable, Integer> specifiedInitialValues = new HashMap<>();
 
    public SoftwareActor(String name, SoftwareActorType type , int capacity)
    {
        super(name, type, capacity);
    }
    

    public Map<IntegerVariable, Integer> InitialValues()
    {
        Map<IntegerVariable, Integer> initalValues = new HashMap<>();
        
        for(Variable variable : Type().Variables())
        {
            if(variable instanceof IntegerVariable)
                initalValues.put((IntegerVariable) variable, 0);
        }
        
        initalValues.putAll(specifiedInitialValues);
        return initalValues;
    }

    public void SetInitialValue(IntegerVariable integerVariable, int i)
    {
        specifiedInitialValues.put(integerVariable, i);
    }
}
