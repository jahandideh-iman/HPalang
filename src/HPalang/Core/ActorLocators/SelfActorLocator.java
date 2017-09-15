/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Core.ActorLocators;

import HPalang.Core.Actor;
import HPalang.Core.ActorLocator;
import HPalang.LTSGeneration.RunTimeStates.ActorState;

/**
 *
 * @author Iman Jahandideh
 */
public class SelfActorLocator  extends ActorLocator<SelfActorLocator>
{

    @Override
    public Actor Locate(ActorState actorState)
    {
        return actorState.Actor();
    }

    @Override
    protected boolean InternalEquals(SelfActorLocator other)
    {
        return true;
    }

    @Override
    protected int InternalHashCode()
    {
        return 0;
    }
    
}
