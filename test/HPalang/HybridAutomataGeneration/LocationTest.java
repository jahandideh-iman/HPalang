/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.HybridAutomataGeneration;


import HPalang.Core.DefferentialEquation;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

/**
 *
 * @author Iman Jahandideh
 */
public class LocationTest
{
    
    @Test
    public void TwoLocationsWithDifferentOrderingOnEquationsAndInvarientsAreEqual()
    {
        Location loc1 = new Location();
        Location loc2 = new Location();
        
        
        loc1.AddEquation(DefferentialEquation.Empty("equ1"));
        loc1.AddEquation(DefferentialEquation.Empty("equ2"));
        loc1.AddInvariant("inv1");
        loc1.AddInvariant("inv2");
        
        loc2.AddEquation(DefferentialEquation.Empty("equ2"));
        loc2.AddEquation(DefferentialEquation.Empty("equ1"));
        loc2.AddInvariant("inv2");
        loc2.AddInvariant("inv1");
        
        assertThat(loc1,equalTo(loc2));
        assertThat(loc1.hashCode(), equalTo(loc2.hashCode()));
    }
    
}
