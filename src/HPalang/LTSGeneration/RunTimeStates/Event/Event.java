/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.RunTimeStates.Event;

import HPalang.Core.ContinuousVariable;
import HPalang.Core.Equalitable;

/**
 *
 * @author Iman Jahandideh
 */
public class Event extends Equalitable<Event>
{

    private final float delay;
    private final ContinuousVariable timer;
    private final Action action;

    public Event(float delay, ContinuousVariable timer, Action action)
    {
        this.delay = delay;
        this.timer = timer;
        this.action = action;
    }
    
    public float Delay()
    {
        return delay;
    }
    
    public ContinuousVariable Timer()
    {
        return timer;
    }
    
    public Action Action()
    {
        return action;
    }

    @Override
    protected boolean InternalEquals(Event other)
    {
        return delay == other.delay
                && timer.equals(other.timer)
                && action.equals(other.action);
    }

    @Override
    protected int InternalHashCode()
    {
        return Float.hashCode(delay) + timer.hashCode() + action.hashCode();
    }

}
