/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Core;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Iman Jahandideh
 */
public abstract class ActorType
{
    private final String name;
    private final Map<String,InstanceParameter> instanceParameters = new HashMap<>();
    private final Map<String,DelegationParameter> delegationParameters = new HashMap<>();
    private final Map<String,Variable> variables = new HashMap<>();
    private final Map<String, MessageHandler> messageHandlers = new HashMap<>();
    
    private final List<Variable.Type> validTypes = new LinkedList<>();
    
    protected ActorType(String name, List<Variable.Type> validTypes)
    {
        this.name = name;
        this.validTypes.addAll(validTypes);
    }
    
    public void AddMessageHandler(String id, MessageHandler handler)
    {
        handler.SetID(id);
        messageHandlers.put(id,handler);
    }
   
    public MessageHandler FindMessageHandler(String messageHandlerName)
    {
        return messageHandlers.get(messageHandlerName);
    }
    
    
    public void AddInstanceParameter(InstanceParameter parameter)
    {
        instanceParameters.put(parameter.Name(),parameter);
    }
    
    public InstanceParameter FindInstanceParameter(String instanceName)
    {
        return instanceParameters.get(instanceName);
    }
    
    public void AddDelegationParameter(DelegationParameter delegationParameter)
    {
        delegationParameters.put(delegationParameter.Name(), delegationParameter);
    }
    
    public DelegationParameter FindDelegationParameter(String delegationName)
    {
        return delegationParameters.get(delegationName);
    }

    public void AddVariable(Variable var)
    {
        AssertValid(var);
        variables.put(var.Name(), var);
    }

    private void AssertValid(Variable var)
    {
        assert (validTypes.contains(var.type()));
    }
    
    public Variable FindVariable(String variableName)
    {
        return variables.get(variableName);
    }

    public boolean HasVariable(String name)
    {
        return FindVariable(name) != null;
    }

    public Collection<Variable> Variables()
    {
        return variables.values();
    }

}
