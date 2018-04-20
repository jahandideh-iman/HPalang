/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.HybridAutomataGeneration;

import HPalang.Core.ContinuousExpressions.DifferentialEquation;
import HPalang.Core.DiscreteExpressions.TrueConst;
import HPalang.LTSGeneration.Labels.Guard;
import HPalang.LTSGeneration.Labels.GuardedlLabel;
import static org.hamcrest.CoreMatchers.*;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Iman Jahandideh
 */
public class HybridAutomatonTest
{
    
    HybridAutomaton automaton = new HybridAutomaton();
    
    @Test
    public void DoesNotAddANewLocationForDuplicateLocation()
    {      
        Location loc = new Location("Loc");
        Location dupLoc = new Location("Loc");
        
        automaton.AddLocation(loc);
        automaton.AddLocation(dupLoc);
        
        assertThat(dupLoc, equalTo(loc));
        assertThat(automaton.GetLocations().size(), equalTo(1));
    }
    
    @Test
    public void DoesNotAddANewTransitionForDuplicateTranstion()
    {
        Location loc1 = new Location("Loc");
        loc1.AddEquation(DifferentialEquation.Empty("eq1"));
        Location Loc2 = new Location("Loc");
        loc1.AddEquation(DifferentialEquation.Empty("eq1"));
        
        HybridLabel label = new HybridLabel(new Guard(new TrueConst()), false);
        
        automaton.AddLocation(loc1);
        automaton.AddLocation(Loc2);
        
        automaton.AddTransition(loc1, label, Loc2);
        automaton.AddTransition(loc1, label, Loc2);
        
        assertThat(automaton.Transitions().size(), equalTo(1));
    }
    
    
    
}
