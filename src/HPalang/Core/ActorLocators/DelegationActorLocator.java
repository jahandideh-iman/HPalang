/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Core.ActorLocators;

import HPalang.Core.Actor;
import HPalang.Core.ActorLocator;
import HPalang.Core.DelegationParameter;
import HPalang.LTSGeneration.RunTimeStates.ActorState;

/**
 *
 * @author Iman Jahandideh
 */
public class DelegationActorLocator extends ActorLocator<DelegationActorLocator>
{
    private final DelegationParameter delegationParameter;

    public DelegationActorLocator(DelegationParameter delegationParameter)
    {
        this.delegationParameter = delegationParameter;
    }
    
    
    @Override
    public Actor Locate(ActorState actorState)
    {
        return actorState.Actor().GetDelegationFor(delegationParameter).Actor();
    }

    @Override
    protected boolean InternalEquals(DelegationActorLocator other)
    {
        return delegationParameter.equals(other.delegationParameter);
    }

    @Override
    protected int InternalHashCode()
    {
        return 0;
    }
    
}
