/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Builders;

import HPalang.LTSGeneration.GlobalRunTimeState;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Iman Jahandideh
 */
public class GlobalRunTimeStateBuilder
{
    private List<ActorRunTimeStateBuilder> actorRunTimeStateBuilders = new ArrayList<>();

    public GlobalRunTimeStateBuilder AddActorRunTimeState(ActorRunTimeStateBuilder actorRunTimeStateBuilder)
    {
        actorRunTimeStateBuilders.add(actorRunTimeStateBuilder);
        return this;
    }

    public GlobalRunTimeState Build()
    {
        GlobalRunTimeState globalState = new GlobalRunTimeState();

        for(ActorRunTimeStateBuilder builder : actorRunTimeStateBuilders)
            globalState.AddActorRunTimeState(builder.Build());

        return globalState;
    }

}
