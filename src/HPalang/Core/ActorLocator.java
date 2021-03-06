/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Core;

import HPalang.LTSGeneration.RunTimeStates.ActorState;

/**
 *
 * @author Iman Jahandideh
 */
public abstract class ActorLocator<T extends ActorLocator> extends Equalitable<T>
{
    public abstract Actor Locate(ActorState actorState);  
}
