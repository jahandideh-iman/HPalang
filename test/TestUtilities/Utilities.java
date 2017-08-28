/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TestUtilities;

import Builders.SoftwareActorStateBuilder;
import Builders.GlobalRunTimeStateBuilder;
import HPalang.LTSGeneration.RunTimeStates.GlobalRunTimeState;
import HPalang.Core.SoftwareActor;
import HPalang.Core.DifferentialEquation;
import HPalang.Core.Mode;
import HPalang.Core.PhysicalActor;
import HPalang.Core.PhysicalActorType;
import HPalang.LTSGeneration.RunTimeStates.SoftwareActorState;
import HPalang.LTSGeneration.RunTimeStates.ContinuousBehavior;
import HPalang.Core.Statement;
import HPalang.LTSGeneration.RunTimeStates.ContinuousState;
import HPalang.LTSGeneration.RunTimeStates.PhysicalActorState;
import HPalang.LTSGeneration.State;
import HPalang.LTSGeneration.StateInfo;
import Mocks.EmptyStatement;
import java.util.Collections;
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
        return CreateSoftwareActorState(actorName, 1);
    }
    
    static public SoftwareActorState CreateSoftwareActorState(String actorName, int capacity)
    {
        SoftwareActorStateBuilder builder = new SoftwareActorStateBuilder().
                WithActor(new SoftwareActor(actorName, capacity));
        
        return builder.Build();
    }
    
    static public SoftwareActor CreateSofwareActor(String actorName)
    {
        return new SoftwareActor(actorName, null);
    }
    
    static public PhysicalActor CreatePhysicalActor(String actorName, Mode ... modes )
    {
        PhysicalActorType type = new PhysicalActorType(actorName + "Type");
        PhysicalActor actor = new PhysicalActor(actorName, type);
        
        for(Mode mode : modes)
            type.AddMode(mode);
        
        return actor;
    }
    
    public static void assertEqualButNotSame(Object obj1,Object obj2)
    {
        assertThat(obj1, equalTo(obj2));
        assertThat(obj1, not(sameInstance(obj2)));
    }
    
    public static GlobalRunTimeState CreateGlobalState()
    {
        GlobalRunTimeStateBuilder builder = new GlobalRunTimeStateBuilder();
        
        return builder.Build();
    }
    
 
        
    public static ContinuousState CreateContinuousState(PhysicalActorState actorState)
    {
        ContinuousState continuousState = new ContinuousState();
        
        continuousState.AddPhysicalActorState(actorState);
        
        return continuousState;
    }
    
    public static PhysicalActorState CreatePhysicalState(String actorName, State substate)
    {
        PhysicalActorState state =  new PhysicalActorState(new PhysicalActor(actorName));
        
        state.AddSubstate(substate);
        
        return state;
    }
        
    public static StateInfo SimpleStateInfo(GlobalRunTimeState globalState)
    {
        return new StateInfo(globalState, Collections.EMPTY_LIST , Collections.EMPTY_LIST);
    }
   
}
