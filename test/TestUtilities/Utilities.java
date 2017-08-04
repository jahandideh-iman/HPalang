/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TestUtilities;

import Builders.ActorRunTimeStateBuilder;
import HPalang.LTSGeneration.RunTimeStates.GlobalRunTimeState;
import HPalang.Core.Actor;
import HPalang.Core.DefferentialEquation;
import HPalang.LTSGeneration.RunTimeStates.ActorRunTimeState;
import HPalang.LTSGeneration.RunTimeStates.ContinuousBehavior;
import HPalang.Core.Statement;
import HPalang.LTSGeneration.RunTimeStates.MessageQueueState;
import Mocks.EmptyMessage;
import Mocks.EmptyStatement;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;

/**
 *
 * @author Iman Jahandideh
 */
public class Utilities
{
    static public ContinuousBehavior EmptyBehavior()
    {
        return new ContinuousBehavior("", DefferentialEquation.Empty(""), "", Statement.StatementsFrom(new EmptyStatement()));
    }
    
    static public ActorRunTimeState NewActorState(String actorName)
    {
        ActorRunTimeStateBuilder builder = new ActorRunTimeStateBuilder().
                WithActor(new Actor(actorName, 0));
        
        return builder.Build();
    }
    
    public static void assertEqualButNotSame(Object obj1,Object obj2)
    {
        assertThat(obj1, equalTo(obj2));
        assertThat(obj1, not(sameInstance(obj2)));
    }
    
    public static GlobalRunTimeState NewGlobalState(ActorRunTimeState ... actorStates)
    {
        GlobalRunTimeState state = new GlobalRunTimeState();
        
        for(ActorRunTimeState actorState : actorStates)
           state.AddActorRunTimeState(actorState);
        
        return state;
    }
    
    public static Actor NewActor(String actorName)
    {
        return new Actor(actorName, 0);
    }
}
