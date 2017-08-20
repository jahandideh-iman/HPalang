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
public class TransitionCollectorMock implements TransitionCollector
{

    private Label expectedLabel;
    private GlobalRunTimeState expectedDestintaiton;
    private boolean expectNothing = false;
    
    public void ExpectTransition(Label label, GlobalRunTimeState destination)
    {
        this.expectedLabel = label;
        this.expectedDestintaiton = destination;
    }
    
    @Override
    public void AddTransition(Label label, GlobalRunTimeState destination)
    {
        assertThat("Expected no Trantions but a trantion is added",expectNothing, is(false));
        assertThat(label, is(equalTo(expectedLabel)));
        assertThat(destination, is(equalTo(expectedDestintaiton)));
    }

    public void ExpectNoTransition()
    {
        expectNothing = true;
    }
    
}
