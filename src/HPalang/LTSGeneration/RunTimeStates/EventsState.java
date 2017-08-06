/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.RunTimeStates;

import HPalang.LTSGeneration.CompositeStateT;
import HPalang.LTSGeneration.RunTimeStates.Event.Event;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Iman Jahandideh
 */
public class EventsState extends CompositeStateT<EventsState>
{
    private final List<Event> events = new LinkedList<>();

    @Override
    protected EventsState NewInstance()
    {
        return new EventsState();
    }

    @Override
    protected void CloneData(EventsState copy)
    {
        copy.events.addAll(events);
    }

    @Override
    protected boolean DataEquals(EventsState other)
    {
        return events.equals(other.events);
    }
 
    public void AddEvent(Event event)
    {
        events.add(event);
    }
    
    public void RemoveEvent(Event event)
    {
       events.remove(event);
    }

    public void SetPool(VariablePoolState poolState)
    {
        assert(FindSubState(VariablePoolState.class) == null);
        AddSubstate(poolState);
    }

    public VariablePoolState PoolState()
    {
        return FindSubState(VariablePoolState.class);
    }

    public Collection<Event> Events()
    {
        return events;
    }

}
