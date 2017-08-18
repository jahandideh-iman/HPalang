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
public class PhysicalActorType extends ActorType
{
    private final String name;
    private final Map<String,ContinuousVariable> variables = new HashMap<>();
    private final Map<String,Mode> modes = new HashMap<>();
    private Mode initialMode;

    public PhysicalActorType(String name)
    {
        this.name = name;
    }
    
    public void AddVariable(ContinuousVariable variable)
    {
        variables.put(variable.Name(),variable);
    }
    
    public void AddMode(Mode mode)
    {
        modes.put(mode.Name(),mode);
    }

    public void SetInitialMode(Mode mode)
    {
        this.initialMode = mode;
    }

    public Mode FindMode(String modeName)
    {
        return modes.get(modeName);
    }

    public ContinuousVariable FindVariable(String variableName)
    {
        return variables.get(variableName);
    }


}
