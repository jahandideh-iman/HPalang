/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Core;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Iman Jahandideh
 */
public class MessageParameters
{
    private final Map<String, VariableParameter> parameters = new HashMap<>();
    
    public void Add(VariableParameter variableParameter)
    {
        parameters.put(variableParameter.Name(),variableParameter);
    }

    public VariableParameter Find(String parameterName)
    {
        return parameters.get(parameterName);
    }
    
    public Set<VariableParameter> ParametersSet()
    {
        return new HashSet<>(parameters.values());
    }
}
