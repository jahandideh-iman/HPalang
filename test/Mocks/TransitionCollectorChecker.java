/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Mocks;

import HPalang.LTSGeneration.Label;
import HPalang.LTSGeneration.RunTimeStates.GlobalRunTimeState;
import HPalang.LTSGeneration.TransitionCollector;
import java.util.LinkedList;
import java.util.List;
import static org.hamcrest.CoreMatchers.*;
import org.hamcrest.core.Is;
import static org.junit.Assert.*;

/**
 *
 * @author Iman Jahandideh
 */
public class TransitionCollectorChecker implements TransitionCollector
{
    
    private final List<Label> collectedLabels = new LinkedList<>();
    private final List<GlobalRunTimeState> collectedGlobalStates = new LinkedList<>();
    
    private int allowTransitions = 1;
    
    public void ExpectTransition(Label label, GlobalRunTimeState destination)
    {
        assertThat(collectedLabels, hasItem(equalTo(label)));
        assertThat(collectedGlobalStates, hasItem(equalTo(destination)));
    }
    
    @Override
    public void AddTransition(Label label, GlobalRunTimeState destination)
    {
        assertTrue(String.format("More than %d transition is added",allowTransitions) , collectedLabels.size() < allowTransitions);
        collectedLabels.add(label);
        collectedGlobalStates.add(destination);
    }

    public void ExpectNoTransition()
    {
        assertTrue("A tranistion was collected.", collectedLabels.size() == 0 && collectedGlobalStates.size() == 0);
    }
    
    public GlobalRunTimeState CollectedGlobalStateAt(int i)
    {
        return collectedGlobalStates.get(i);
    }
    
    public Label CollectedLabelAt(int i)
    {
        return collectedLabels.get(i);
    }

    public void SetAllowedTransitions(int count)
    {
        allowTransitions = count;
    }
    
    
}
