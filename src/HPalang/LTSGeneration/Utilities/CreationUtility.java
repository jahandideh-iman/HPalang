/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.Utilities;

import HPalang.Core.Actor;
import HPalang.Core.SoftwareActor;
import HPalang.LTSGeneration.Builders.GlobalRunTimeStateBuilder;
import HPalang.LTSGeneration.Builders.SoftwareActorStateBuilder;
import HPalang.LTSGeneration.RunTimeStates.GlobalRunTimeState;
import HPalang.LTSGeneration.RunTimeStates.SoftwareActorState;

/**
 *
 * @author Iman Jahandideh
 */
public class CreationUtility
{
    static public GlobalRunTimeState CreateEmptyGlobalState()
    {
        GlobalRunTimeStateBuilder builder = new GlobalRunTimeStateBuilder();
        
        return builder.Build();
    }
    
    static public SoftwareActorState CreateSoftwareState(SoftwareActor actor)
    {
        return new SoftwareActorStateBuilder().WithActor(actor).Build();
    }
}