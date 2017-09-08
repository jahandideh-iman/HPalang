/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Core.ActorLocators;

import HPalang.Core.Actor;
import HPalang.Core.ActorLocator;
import HPalang.Core.InstanceParameter;

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
    
    @Override
    public Actor GetActor()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
