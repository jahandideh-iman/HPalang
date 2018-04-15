/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration;

import HPalang.Core.TransitionSystem.Transition;
import HPalang.Core.Equalitable;
import HPalang.LTSGeneration.RunTimeStates.GlobalRunTimeState;
import java.util.Collection;

/**
 *
 * @author Iman Jahandideh
 */
public class StateInfo extends Equalitable<StateInfo>
{
    private final GlobalRunTimeState state;
    private final Collection<Transition<GlobalRunTimeState>> ins;
    private final Collection<Transition<GlobalRunTimeState>> outs;
    
    public StateInfo(GlobalRunTimeState state, 
            Collection<Transition<GlobalRunTimeState>> ins, 
            Collection<Transition<GlobalRunTimeState>> outs)
    {
        this.state = state;
        this.ins = ins;
        this.outs = outs;
    }

    public GlobalRunTimeState State()
    {
        return state;
    }

    public Collection<Transition<GlobalRunTimeState>> Outs()
    {
        return outs;
    }

    @Override
    protected boolean InternalEquals(StateInfo other)
    {
        return this.state.equals(other.state) &&
                this.outs.equals(other.outs) &&
                this.ins.equals(other.ins);
    }

    @Override
    protected int InternalHashCode()
    {
        return 0;
    }
}
