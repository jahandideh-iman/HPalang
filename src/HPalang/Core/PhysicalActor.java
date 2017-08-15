/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Core;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Iman Jahandideh
 */
public class PhysicalActor 
{
    private final String name;
    private final Set<Mode> modes = new HashSet<>();
    private final Set<ContinuousVariable> variables = new HashSet<>();

    public PhysicalActor(String name)
    {
        this.name = name;
    }
    
    public void AddVariable(ContinuousVariable variable)
    {
        variables.add(variable);
    }
    public void AddMode(Mode mode)
    {
        modes.add(mode);
    }
}
