/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Core.Messages;

import HPalang.Core.Equalitable;
import HPalang.Core.Message;
import HPalang.Core.MessageParameters;
import HPalang.Core.Mode;
import HPalang.Core.Statement;
import HPalang.Core.Statements.ModeChangeStatement;
import java.util.Queue;

/**
 *
 * @author Iman Jahandideh
 */
public class SetModeMessage extends Equalitable<SetModeMessage> implements Message
{
    static final MessageParameters EMPTY_MESSAGE_PARAMS  = new MessageParameters();
    

    private final Queue<Statement> changeModeStatements;
    
    public SetModeMessage(Mode mode)
    {
        this.changeModeStatements = Statement.StatementsFrom(new ModeChangeStatement(mode));
    }

    @Override
    public Queue<Statement> GetMessageBody()
    {
        return changeModeStatements;
    }

    @Override
    public MessageParameters Parameters()
    {
        return EMPTY_MESSAGE_PARAMS;
    }

    @Override
    public MessageType MessageType()
    {
        return MessageType.Control;
    }
    
    @Override
    protected boolean InternalEquals(SetModeMessage other)
    {
        return true;
    }

    @Override
    protected int InternalHashCode()
    {
        return 1;
    }
    
}
