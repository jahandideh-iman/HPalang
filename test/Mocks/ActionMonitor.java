/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Mocks;

import HPalang.LTSGeneration.RunTimeStates.Event.Action;
import HPalang.LTSGeneration.RunTimeStates.GlobalRunTimeState;

/**
 *
 * @author Iman Jahandideh
 */
public class ActionMonitor implements Action
{
    public boolean isExecuted = false;
    @Override
    public void Execute(GlobalRunTimeState globalState)
    {
        isExecuted = true;
    }
    
}
