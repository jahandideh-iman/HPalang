/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Builders;

import HPalang.LTSGeneration.State;
import HPalang.LTSGeneration.RunTimeStates.ActorRunTimeState;
import HPalang.LTSGeneration.RunTimeStates.GlobalRunTimeState;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Iman Jahandideh
 */
public class GlobalRunTimeStateBuilder
{
    private final List<ActorRunTimeStateBuilder> actorRunTimeStateBuilders = new LinkedList<>();
    private final List<ActorRunTimeState> actorRunTimeStates = new LinkedList<>();
    private final List<State> substates = new LinkedList<>();

    public GlobalRunTimeStateBuilder AddActorRunTimeState(ActorRunTimeStateBuilder actorRunTimeStateBuilder)
    {
        actorRunTimeStateBuilders.add(actorRunTimeStateBuilder);
        return this;
    }
    
    public GlobalRunTimeStateBuilder AddActorRunTimeState(ActorRunTimeState actorRunTimeState)
    {
        actorRunTimeStates.add(actorRunTimeState);
        return this;
    }
    
    public GlobalRunTimeStateBuilder AddSubstate(State substate)
    {
        substates.add(substate);
        return this;
    }

    public GlobalRunTimeState Build()
    {
        GlobalRunTimeState globalState = new GlobalRunTimeState();

        for(ActorRunTimeStateBuilder builder : actorRunTimeStateBuilders)
            globalState.AddActorRunTimeState(builder.Build());
        for(ActorRunTimeState state : actorRunTimeStates)
            globalState.AddActorRunTimeState(state);
        for(State substate : substates)
            globalState.AddSubstate(substate);
        //actorRunTimeStates.forEach((state)->globalState.AddActorRunTimeState(state));
        //actorRunTimeStateBuilders.forEach((builder) -> globalState.AddActorRunTimeState(builder.Build()));
        //substates.forEach((state) -> globalState.AddSubstate(state));

        return globalState;
    }

}
