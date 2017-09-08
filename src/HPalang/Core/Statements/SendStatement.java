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
import HPalang.Core.MessageLocator;
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
    
    private final MessageLocator messageLocator;
    private final ActorLocator receiverLocator;
    private final MessageArguments arguments;
    
    public SendStatement(ActorLocator receiverLocator, MessageLocator messageLocator, MessageArguments arguments)
    {
        if(arguments.Match(messageLocator.Parameters()) == false)
            throw new ArgumentsDoesNotMatchException();
        this.receiverLocator = receiverLocator;
        this.messageLocator = messageLocator;
        this.arguments = arguments;
    }
    
    public SendStatement(ActorLocator receiverLocator, MessageLocator messageLocator)
    {
        this(receiverLocator, messageLocator, new MessageArguments());
    }

    
    public ActorLocator ReceiverLocator()
    {
        return receiverLocator;
    }
    
    public MessageLocator MessageLocator()
    {
        return messageLocator;
    }
    
    public MessageArguments Arguments()
    {
        return arguments;
    }
    
    @Override
    protected boolean InternalEquals(SendStatement other)
    {
        return this.receiverLocator.equals(other.receiverLocator) &&
                this.messageLocator.equals(other.messageLocator) &&
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
        return "(" + receiverLocator.GetActor().Name() + "!" + messageLocator.toString() + ")";
    }
}
