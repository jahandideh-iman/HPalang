/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Mocks;

import HPalang.Core.Equalitable;
import HPalang.LTSGeneration.RunTimeStates.Event.Action;
import HPalang.LTSGeneration.RunTimeStates.GlobalRunTimeState;

/**
 *
 * @author Iman Jahandideh
 */
public class ActionMonitor extends Equalitable<ActionMonitor> implements Action 
{
    public boolean isExecuted = false;
    @Override
    public void Execute(GlobalRunTimeState globalState)
    {
        isExecuted = true;
    }

    @Override
    protected boolean InternalEquals(ActionMonitor other)
    {
        return true;
    }

    @Override
    protected int InternalHashCode()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
