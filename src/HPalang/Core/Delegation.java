/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Core;

/**
 *
 * @author Iman Jahandideh
 */
public class Delegation extends Equalitable<Delegation>
{
    private final Actor actor;
    private final MessageHandler messageHandler;
    
    public Delegation(Actor actor, MessageHandler messageHandler)
    {
        this.actor = actor;
        this.messageHandler = messageHandler;
    }
    
    public Actor Actor()
    {
        return actor;
    }
    
    public MessageHandler MessageHandler()
    {
        return messageHandler;
    }

    @Override
    protected boolean InternalEquals(Delegation other)
    {
        return actor.equals(other.actor) &&
                messageHandler.equals(other.messageHandler);
    }

    @Override
    protected int InternalHashCode()
    {
        return actor.hashCode() + messageHandler.hashCode();
    }
}
