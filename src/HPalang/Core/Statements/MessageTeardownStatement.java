/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Core.Statements;

import HPalang.Core.MessageParameters;
import HPalang.Core.Variables.RealVariable;
import java.util.List;

/**
 *
 * @author Iman Jahandideh
 */
public class MessageTeardownStatement extends AbstractStatement<MessageTeardownStatement>
{
    private final MessageParameters parametersToRemove;
    private final List<RealVariable> variablesToRelease;
    
    public MessageTeardownStatement(MessageParameters parametersToRemove, List<RealVariable> argumentsToRelease)
    {
        this.parametersToRemove = parametersToRemove;
        this.variablesToRelease = argumentsToRelease;
    }
    
    @Override
    protected boolean InternalEquals(MessageTeardownStatement other)
    {
        return this.parametersToRemove.equals(other.parametersToRemove)
                && this.variablesToRelease.equals(other.variablesToRelease);
    }

    @Override
    protected int InternalHashCode()
    {
        return parametersToRemove.hashCode() + variablesToRelease.hashCode();
    }

    public MessageParameters ParametersToRemove()
    {
        return parametersToRemove;
    }
    
    public List<RealVariable> VariablesToRelease()
    {
        return variablesToRelease;
    }
    
}
