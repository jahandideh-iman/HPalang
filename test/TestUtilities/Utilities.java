/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TestUtilities;

import Builders.ActorRunTimeStateBuilder;
import Builders.GlobalRunTimeStateBuilder;
import HPalang.LTSGeneration.RunTimeStates.GlobalRunTimeState;
import HPalang.Core.SoftwareActor;
import HPalang.Core.DifferentialEquation;
import HPalang.LTSGeneration.RunTimeStates.SoftwareActorState;
import HPalang.LTSGeneration.RunTimeStates.ContinuousBehavior;
import HPalang.Core.Statement;
import HPalang.LTSGeneration.RunTimeStates.DiscreteState;
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
        return new ContinuousBehavior("", DifferentialEquation.Empty(""), "", Statement.StatementsFrom(new EmptyStatement()));
    }
    
    static public SoftwareActorState CreateSoftwareActorState(String actorName)
    {
        ActorRunTimeStateBuilder builder = new ActorRunTimeStateBuilder().
                WithActor(new SoftwareActor(actorName, 0));
        
        return builder.Build();
    }
    
    static public SoftwareActor CreateSofwareActor(String actorName)
    {
        return new SoftwareActor(actorName, null);
    }
    
    public static void assertEqualButNotSame(Object obj1,Object obj2)
    {
        assertThat(obj1, equalTo(obj2));
        assertThat(obj1, not(sameInstance(obj2)));
    }
    
    public static GlobalRunTimeState CreateGlobalState(SoftwareActorState ... actorStates)
    {
        GlobalRunTimeStateBuilder builder = new GlobalRunTimeStateBuilder();
        
        for(SoftwareActorState actorState : actorStates)
            builder.AddSoftwareActorState(actorState);
   
        return builder.Build();
    }
    
    public static SoftwareActor NewActor(String actorName)
    {
        return new SoftwareActor(actorName, 0);
    }
}
