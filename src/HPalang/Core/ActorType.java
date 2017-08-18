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
public class ActorType
{
    private final Map<String,InstanceParameter> instanceParameters = new HashMap<>();
    
    public void AddInstanceParameter(InstanceParameter parameter)
    {
        instanceParameters.put(parameter.Name(),parameter);
    }
    
    public InstanceParameter FindInstanceParameter(String controller)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
