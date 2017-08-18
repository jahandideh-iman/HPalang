/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Core;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/**
 *
 * @author Iman Jahandideh
 */
public class MessageHandler
{
    private final Queue<Statement> statements = new LinkedList<>();
    private SoftwareActor owner;
    private String id;
    private final Map<String, VariableParameter> parameters = new HashMap<>();
    
    public MessageHandler()
    {
    }
    
    public void SetOwner(SoftwareActor owner)
    {
        this.owner = owner;
    }
    
    public void AddStatement(Statement statement)
    {
        statements.add(statement);
    }
    
    public Queue<Statement> GetBody()
    {
        return statements;
    }
    
    public void SetID(String id)
    {
        this.id = id;
    }

    public String GetID()
    {
       return id;
    }

    public void AddParameter(VariableParameter variableParameter)
    {
        parameters.put(variableParameter.Name(),variableParameter);
    }

    public VariableParameter FindVariableParameter(String parameterName)
    {
        return parameters.get(parameterName);
    }
}
