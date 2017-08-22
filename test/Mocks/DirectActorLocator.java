/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Mocks;

import HPalang.Core.Actor;
import HPalang.Core.ActorLocator;

/**
 *
 * @author Iman Jahandideh
 */
public class DirectActorLocator extends ActorLocator<DirectActorLocator>
{
    private Actor actor;
    
    public DirectActorLocator(Actor actor)
    {
        this.actor = actor;
    }
    
    @Override
    public Actor GetActor()
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
       return 0;
    }
    
}
