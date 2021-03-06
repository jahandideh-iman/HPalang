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
public class DirectActorLocator extends ActorLocator<DirectActorLocator>
{
    private final Actor actor;
    
    public DirectActorLocator(Actor actor)
    {
        this.actor = actor;
    }
    
    @Override
    public Actor Locate(ActorState actorState)
    {
        return actor;
    }

    @Override
    protected boolean InternalEquals(DirectActorLocator other)
    {
        return this.actor.equals(other.actor);
    }

    @Override
    protected int InternalHashCode()
    {
       return actor.hashCode();
    }
    
}
