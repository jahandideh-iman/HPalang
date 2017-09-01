/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.RunTimeStates.Event;

import HPalang.Core.SoftwareActor;
import HPalang.LTSGeneration.RunTimeStates.GlobalRunTimeState;
import HPalang.LTSGeneration.RunTimeStates.SoftwareActorState;
import TestUtilities.CoreUtility;
import static TestUtilities.CoreUtility.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Iman Jahandideh
 */
public class ResumeSoftwareActorActionTest
{
    
    @Test
    public void ResumesTheActorStateForTheGivenActor()
    {
        GlobalRunTimeState globalState = CreateGlobalState();
       
        SoftwareActorState actorState = CreateSoftwareActorState("actor");
        AddActorState(actorState, globalState);
        
        actorState.SetSuspended(true);
        
        Action action = new ResumeSoftwareActorAction(actorState.SActor());

        action.Execute(globalState);
        
        assertThat(actorState.IsSuspended(), is(false));   
    }
    
    @Test
    public void ActionsWithEqualDataAreEqual()
    {
        SoftwareActor actor = CreateSofwareActor("actor");
        
        Action action1 = new ResumeSoftwareActorAction(actor);
        Action action2 = new ResumeSoftwareActorAction(actor);
        
        assertThat(action1, equalTo(action2));   
    }
    
}
