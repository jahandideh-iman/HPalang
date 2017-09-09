/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.Utilities;

import HPalang.Core.PhysicalActor;
import HPalang.Core.SoftwareActor;
import HPalang.LTSGeneration.Builders.*;
import HPalang.LTSGeneration.RunTimeStates.*;
import java.util.Collection;

/**
 *
 * @author Iman Jahandideh
 */
public class FillerUtility
{
    static public void FillDiscreteState(GlobalRunTimeState globalState, Collection<SoftwareActor> actors)
    {
        DiscreteState discreteState = globalState.DiscreteState();
        
        for(SoftwareActor actor : actors)
        {
            SoftwareActorStateBuilder builder = new SoftwareActorStateBuilder().WithActor(actor);
            discreteState.AddSoftwareActorState(builder.Build());
        }
    }
    
    static public void FillContinuousState(GlobalRunTimeState globalState, Collection<PhysicalActor> actors)
    {
        ContinuousState continuousState = globalState.ContinuousState();
        
        for(PhysicalActor actor : actors)
        {
            PhysicalActorStateBuilder builder = new PhysicalActorStateBuilder()
                    .WithActor(actor)
                    .WithMode(actor.InitialMode());
            continuousState.AddPhysicalActorState(builder.Build());
        }
    }
}
