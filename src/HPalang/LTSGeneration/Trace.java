/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration;

import HPalang.Core.TransitionSystem.Transition;
import HPalang.LTSGeneration.RunTimeStates.GlobalRunTimeState;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Iman Jahandideh
 */
public class Trace
{
    private List<GlobalRunTimeState> states = new LinkedList<>();
    private List<Transition> transitions = new LinkedList<>();
    
    public Trace(GlobalRunTimeState state)
    {
        states.add(state);
    }
    
    public Trace(Trace trace)
    {
        states.addAll(trace.states);
        transitions.addAll(trace.transitions);
    }
    
    public void InsertTransition(Transition tr, int index)
    {
        transitions.add(index, tr);
    }
    
    public void InsertState(GlobalRunTimeState state, int index)
    {
        states.add(index, state);
    }
    
    public GlobalRunTimeState GetLastState()
    {
        return states.get(states.size()-1);
    }
    
    public List<Transition> GetTransitions()
    {
        return transitions;
    }
}
