/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.RunTimeStates.Event;

import HPalang.LTSGeneration.RunTimeStates.GlobalRunTimeState;

/**
 *
 * @author Iman Jahandideh
 */
public interface Action
{
    void Execute(GlobalRunTimeState globalState);

    public boolean IsDeadlock(GlobalRunTimeState globalState);
}