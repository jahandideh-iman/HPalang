/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Mocks;

import HPalang.LTSGeneration.Label;
import HPalang.LTSGeneration.RunTimeStates.GlobalRunTimeState;
import HPalang.LTSGeneration.TransitionCollector;
import static org.hamcrest.CoreMatchers.*;
import org.hamcrest.core.Is;
import static org.junit.Assert.*;

/**
 *
 * @author Iman Jahandideh
 */
public class TransitionCollectorChecker implements TransitionCollector
{

    
    public Label collectedLabel;
    public GlobalRunTimeState collectedGlobalState;
    
    public void ExpectTransition(Label label, GlobalRunTimeState destination)
    {
        assertThat(collectedLabel, is(equalTo(label)));
        assertThat(collectedGlobalState, is(equalTo(destination)));
    }
    
    @Override
    public void AddTransition(Label label, GlobalRunTimeState destination)
    {
        assertTrue("More than one transition is added", collectedLabel == null && collectedGlobalState == null);
        collectedLabel = label;
        collectedGlobalState = destination;
    }

    public void ExpectNoTransition()
    {
        assertTrue("A tranistion was collected.", collectedLabel == null && collectedGlobalState == null);
    }
    
    
}
