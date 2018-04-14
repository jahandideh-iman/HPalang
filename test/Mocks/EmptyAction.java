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
public class EmptyAction extends Equalitable<EmptyAction> implements Action 
{

    @Override
    public void Execute(GlobalRunTimeState globalState)
    {
        
    }

    @Override
    protected boolean InternalEquals(EmptyAction other)
    {
        return true;
    }

    @Override
    protected int InternalHashCode()
    {
        return 0;
    }

    @Override
    public boolean IsDeadlock(GlobalRunTimeState State)
    {
        return false;
    }
    
}
