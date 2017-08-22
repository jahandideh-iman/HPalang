/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Core;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Iman Jahandideh
 */
public class PhysicalActorType extends ActorType
{

    private final Map<String,Mode> modes = new HashMap<>();
    private Mode initialMode;

    public PhysicalActorType(String name)
    {
        super(name, Arrays.asList(Variable.Type.real));
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
}
