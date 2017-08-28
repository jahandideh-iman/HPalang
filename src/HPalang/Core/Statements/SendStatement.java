/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Core.Statements;

import HPalang.Core.ActorLocator;
import HPalang.Core.MessageArguments;
import HPalang.Core.SoftwareActor;
import HPalang.Core.Message;
import java.io.UncheckedIOException;

/**
 *
 * @author Iman Jahandideh
 */
public class SendStatement extends AbstractStatement<SendStatement>
{
    public class ArgumentsDoesNotMatchException extends RuntimeException
    {
    }
    
    private final Message message;
    private final ActorLocator receiverLocator;
    private final MessageArguments arguments;
    
    public SendStatement(ActorLocator receiverLocator, Message message, MessageArguments arguments)
    {
        if(arguments.Match(message.Parameters()) == false)
            throw new ArgumentsDoesNotMatchException();
        this.receiverLocator = receiverLocator;
        this.message = message;
        this.arguments = arguments;
    }
    
    public SendStatement(ActorLocator receiverLocator, Message message)
    {
        this(receiverLocator, message, new MessageArguments());
    }

    
    public SoftwareActor Receiver()
    {
        return (SoftwareActor) receiverLocator.GetActor();
    }
    
    public Message Message()
    {
        return message;
    }
    
    public MessageArguments Arguments()
    {
        return arguments;
    }
    
    @Override
    protected boolean InternalEquals(SendStatement other)
    {
        return this.receiverLocator.equals(other.receiverLocator) &&
                this.message.equals(other.message) &&
                this.arguments.equals(other.arguments);
    }

    @Override
    protected int InternalHashCode()
    {
        return 1;
    }
    
    @Override
    public String toString()
    {
        return "(" + receiverLocator.GetActor().Name() + "!" + message.toString() + ")";
    }
}
