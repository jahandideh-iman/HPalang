/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.HybridAutomataGeneration;


import HPalang.Core.ContinuousExpressions.DifferentialEquation;
import HPalang.Core.ContinuousExpressions.Invarient;
import Mocks.EmptyExpression;
import HPalang.Core.NullExpression;
import static TestUtilities.CoreUtility.FakeInvarient;
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
        Location loc1 = new Location("Loc");
        Location loc2 = new Location("Loc");
        
        
        loc1.AddEquation(DifferentialEquation.Empty("equ1"));
        loc1.AddEquation(DifferentialEquation.Empty("equ2"));
        loc1.AddInvariant(FakeInvarient("inv1"));
        loc1.AddInvariant(FakeInvarient("inv2"));
        
        loc2.AddEquation(DifferentialEquation.Empty("equ2"));
        loc2.AddEquation(DifferentialEquation.Empty("equ1"));
        loc2.AddInvariant(FakeInvarient("inv2"));
        loc2.AddInvariant(FakeInvarient("inv1"));
        
        assertThat(loc1,equalTo(loc2));
        assertThat(loc1.hashCode(), equalTo(loc2.hashCode()));
    }
    

    
}
