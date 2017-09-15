/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.Builders;

import HPalang.Core.NullRealVariablePool;
import HPalang.LTSGeneration.RunTimeStates.ContinuousState;
import HPalang.LTSGeneration.RunTimeStates.DiscreteState;
import HPalang.LTSGeneration.RunTimeStates.EventsState;
import HPalang.LTSGeneration.RunTimeStates.GlobalRunTimeState;
import HPalang.LTSGeneration.RunTimeStates.NetworkState;
import HPalang.LTSGeneration.RunTimeStates.SoftwareActorState;
import HPalang.LTSGeneration.RunTimeStates.VariablePoolState;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Iman Jahandideh
 */
public class GlobalRunTimeStateBuilder
{
    private final List<SoftwareActorState> softwareActorStates = new LinkedList<>();
 
    public GlobalRunTimeStateBuilder AddSoftwareActorState(SoftwareActorState SoftwareActorState)
    {
        softwareActorStates.add(SoftwareActorState);
        return this;
    }
    
    public GlobalRunTimeState Build()
    {
        GlobalRunTimeState globalState = new GlobalRunTimeState();

        DiscreteState discreteState = new DiscreteState();
        ContinuousState continuosState = new ContinuousState();
        EventsState eventState = new EventsState();
        eventState.AddSubstate(new VariablePoolState(new NullRealVariablePool()));
        
        globalState.AddSubstate(discreteState);
        globalState.AddSubstate(continuosState);
        globalState.AddSubstate(new VariablePoolState(new NullRealVariablePool()));
        globalState.AddSubstate(new NetworkState(10));
        globalState.AddSubstate(eventState);
        
        for(SoftwareActorState state : softwareActorStates)
            discreteState.AddSoftwareActorState(state);

        return globalState;
    }

}
