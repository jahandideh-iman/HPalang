/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.RunTimeStates;

import HPalang.LTSGeneration.CompositeStateT;
import HPalang.LTSGeneration.RunTimeStates.Event.Action;
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

    public EventsState()
    {
    }
    
    @Override
    protected EventsState NewInstance()
    {
        return new EventsState();
    }
    
    public Event RegisterEvent(float delay, Action action)
    {
        Event event = new Event(delay, PoolState().Pool().Acquire(), action);
        AddEvent(event);
        return event;
    }

    public void UnregisterEvent(Event event)
    {
        PoolState().Pool().Release(event.Timer());
        RemoveEvent(event);
    }
    
    private void AddEvent(Event event)
    {
        events.add(event);
    }
    
    private void RemoveEvent(Event event)
    {
       events.remove(event);
    }

    private void SetPool(VariablePoolState poolState)
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

    @Override
    protected int DataHashCode()
    {
        return events.hashCode();
    }
    
    
}
