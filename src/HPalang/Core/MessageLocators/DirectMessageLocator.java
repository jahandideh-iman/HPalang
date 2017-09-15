/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Core.MessageLocators;

import HPalang.Core.Actor;
import HPalang.Core.Message;
import HPalang.Core.MessageParameters;

/**
 *
 * @author Iman Jahandideh
 */
public class DirectMessageLocator extends MessageLocatorT<DirectMessageLocator>
{
    private final Message message;
    
    public DirectMessageLocator(Message message)
    {
        this.message = message;
    }
    
    @Override
    public Message Locate(Actor actor)
    {
        return message;
    }
    
    @Override
    public MessageParameters Parameters()
    {
        return message.Parameters();
    }
    
    @Override
    protected boolean InternalEquals(DirectMessageLocator other)
    {
        return this.message.equals(other.message);
    }

    @Override
    protected int InternalHashCode()
    {
        return 0;
    }


}
