/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.RunTimeStates;

import HPalang.Core.PhysicalActor;
import org.junit.Test;
import static org.junit.Assert.*;
import static TestUtilities.Utilities.assertEqualButNotSame;


/**
 *
 * @author Iman Jahandideh
 */
public class ContinuousStateTest
{
    
    @Test
    public void ClonesThePhysicalActorStates()
    {
        ContinuousState original = new ContinuousState();
        PhysicalActorState actorState1 = new PhysicalActorState(new PhysicalActor("actor1"));       
        PhysicalActorState actorState2 = new PhysicalActorState(new PhysicalActor("actor2"));
        
        original.AddPhysicalActorState(actorState1);        
        original.AddPhysicalActorState(actorState2);
        
        ContinuousState copy = original.DeepCopy();

        assertEqualButNotSame(original,copy);
        assertEqualButNotSame(actorState1, copy.FindActorState(actorState1.Actor())); 
        assertEqualButNotSame(actorState2, copy.FindActorState(actorState2.Actor()));
    }
    
}
