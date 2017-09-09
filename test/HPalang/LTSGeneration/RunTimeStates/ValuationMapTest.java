/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.RunTimeStates;

import HPalang.Core.ValuationContainers.SimpleValuationContainer;
import HPalang.Core.Statement;
import HPalang.Core.Variables.IntegerVariable;
import org.hamcrest.core.Is;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

/**
 *
 * @author Iman Jahandideh
 */
public class ValuationMapTest
{
    
    @Test
    public void HasTheAddedVariables()
    {
        SimpleValuationContainer valuation = new SimpleValuationContainer();
        
        IntegerVariable var = new IntegerVariable("Var");
        
        valuation.Add(var);
        
        assertThat(valuation.Has(var), is(true));
    }
    
    @Test
    public void SetsVariableValue()
    {
        SimpleValuationContainer valuation = new SimpleValuationContainer();
        
        IntegerVariable var = new IntegerVariable("Var");
        
        valuation.Add(var);
        valuation.Set(var, 5);
        
        assertThat(valuation.ValueFor(var), equalTo(5));
    }
    
    @Test
    public void MapsWithEqualValuationsAreEqual()
    {
        SimpleValuationContainer valuation1 = new SimpleValuationContainer();
        SimpleValuationContainer valuation2 = new SimpleValuationContainer();

        valuation1.Set(new IntegerVariable("var"), 5);
        valuation2.Set(new IntegerVariable("var"), 5);
        
        assertThat(valuation1, equalTo(valuation2));
    }
}
