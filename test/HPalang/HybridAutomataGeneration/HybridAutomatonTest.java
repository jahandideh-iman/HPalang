/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.HybridAutomataGeneration;

import HPalang.Core.DifferentialEquation;
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
        Location loc = new Location();
        Location dupLoc = new Location();
        
        automaton.AddLocation(loc);
        automaton.AddLocation(dupLoc);
        
        assertThat(dupLoc, equalTo(loc));
        assertThat(automaton.GetLocations().size(), equalTo(1));
    }
    
    @Test
    public void DoesNotAddANewTransitionForDuplicateTranstion()
    {
        Location loc1 = new Location();
        loc1.AddEquation(DifferentialEquation.Empty("eq1"));
        Location Loc2 = new Location();
        loc1.AddEquation(DifferentialEquation.Empty("eq1"));
        
        automaton.AddLocation(loc1);
        automaton.AddLocation(Loc2);
        
        automaton.AddTransition(loc1, new GuardedlLabel("Cond"), Loc2);
        automaton.AddTransition(loc1, new GuardedlLabel("Cond"), Loc2);
        
        assertThat(automaton.GetTransitions().size(), equalTo(1));
    }
    
}
