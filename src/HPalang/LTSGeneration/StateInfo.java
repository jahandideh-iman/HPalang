/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration;

import HPalang.LTSGeneration.RunTimeStates.GlobalRunTimeState;
import java.util.Collection;

/**
 *
 * @author Iman Jahandideh
 */
public class StateInfo
{
    private final GlobalRunTimeState state;
    private final Collection<Transition> ins;
    private final Collection<Transition> outs;
    
    public StateInfo(GlobalRunTimeState state, Collection<Transition> ins, Collection<Transition> outs)
    {
        this.state = state;
        this.ins = ins;
        this.outs = outs;
    }

    public GlobalRunTimeState State()
    {
        return state;
    }

    public Collection<Transition> Outs()
    {
        return outs;
    }
}
