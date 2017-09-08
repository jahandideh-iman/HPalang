/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Core.MessageLocators;

import HPalang.Core.Actor;
import HPalang.Core.DelegationParameter;
import HPalang.Core.Message;
import HPalang.Core.MessageParameters;

/**
 *
 * @author Iman Jahandideh
 */
public class DelegationMessageLocator extends MessageLocatorT<DelegationMessageLocator>
{
    private final DelegationParameter delegationParameter;

    public DelegationMessageLocator(DelegationParameter delegationParameter)
    {
        this.delegationParameter = delegationParameter;
    }
    
    @Override
    public Message Get(Actor actor)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public MessageParameters Parameters()
    {
        return delegationParameter.Parameters();
    }

    @Override
    protected boolean InternalEquals(DelegationMessageLocator other)
    {
        return this.delegationParameter.equals(other.delegationParameter);
    }

    @Override
    protected int InternalHashCode()
    {
        return 0;
    }
  
}
