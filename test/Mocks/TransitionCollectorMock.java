/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Mocks;

import HPalang.LTSGeneration.Label;
import HPalang.LTSGeneration.RunTimeStates.GlobalRunTimeState;
import HPalang.LTSGeneration.TransitionCollector;

/**
 *
 * @author Iman Jahandideh
 */
public class TransitionCollectorMock implements TransitionCollector
{
    
    public GlobalRunTimeState collectedState;
    
    @Override
    public void AddTransition(Label label, GlobalRunTimeState destination)
    {
        collectedState = destination;
    }
    
}
