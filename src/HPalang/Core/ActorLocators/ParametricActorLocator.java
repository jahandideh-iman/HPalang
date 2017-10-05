/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Core.ActorLocators;

import HPalang.Core.Actor;
import HPalang.Core.ActorLocator;
import HPalang.Core.InstanceParameter;
import HPalang.LTSGeneration.RunTimeStates.ActorState;

/**
 *
 * @author Iman Jahandideh
 */
public class ParametricActorLocator extends ActorLocator<ParametricActorLocator>
{
    private final InstanceParameter instanceParameter;
    
    public ParametricActorLocator(InstanceParameter instanceParameter)
    {
        this.instanceParameter = instanceParameter;
    }
    
    public InstanceParameter InstanceParameter()
    {
        return instanceParameter;
    }
    
    @Override
    public Actor Locate(ActorState actorState)
    {
        return actorState.Actor().GetInstanceFor(instanceParameter);
    }

    @Override
    protected boolean InternalEquals(ParametricActorLocator other)
    {
        return instanceParameter.equals(other.instanceParameter);
    }

    @Override
    protected int InternalHashCode()
    {
        return 0;
    }
}
