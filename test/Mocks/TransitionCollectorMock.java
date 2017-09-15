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

/**
 *
 * @author Iman Jahandideh
 */
public class TransitionCollectorMock implements TransitionCollector
{
    
    public final List<Label> collectedLabels = new LinkedList<>();
    public final List<GlobalRunTimeState> collectedGlobalStates = new LinkedList<>();
    
    
    @Override
    public void AddTransition(Label label, GlobalRunTimeState destination)
    {
        collectedLabels.add(label);
        collectedGlobalStates.add(destination);
    }
    
    public GlobalRunTimeState GetState(int index)
    {
        return collectedGlobalStates.get(index);
    }
    
    public Label GetLabel(int index)
    {
        return collectedLabels.get(index);
    }
    
}
