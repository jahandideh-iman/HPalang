/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Core.ActorLocators;

import HPalang.Core.Actor;
import HPalang.Core.ActorLocator;
import HPalang.Core.DelegationParameter;

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
    public Actor GetActor()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected boolean InternalEquals(DelegationActorLocator other)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected int InternalHashCode()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
