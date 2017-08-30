/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Core.Statements;

import HPalang.Core.MessageParameters;
import HPalang.Core.VariableParameter;
import java.util.Set;

/**
 *
 * @author Iman Jahandideh
 */
public class MessageTeardownStatement extends AbstractStatement<MessageTeardownStatement>
{
    private final MessageParameters parametersToRemove;
    
    public MessageTeardownStatement(MessageParameters parametersToRemove)
    {
        this.parametersToRemove = parametersToRemove;
    }
    @Override
    protected boolean InternalEquals(MessageTeardownStatement other)
    {
        return this.parametersToRemove.equals(other.parametersToRemove);
    }

    @Override
    protected int InternalHashCode()
    {
        return 0;
    }

    public MessageParameters ParametersToRemove()
    {
        return parametersToRemove;
    }
    
}
