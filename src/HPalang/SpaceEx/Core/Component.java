/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.SpaceEx.Core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Iman Jahandideh
 */
public abstract class Component implements Visitable
{
    Set<Parameter> parameters = new HashSet<>();
    private String id;
    
    public Component(String id)
    {
        this.id = id;
    }
    
    public String GetID()
    {
        return id;
    }
    
    public void AddParameter(Parameter parameter)
    {
        parameters.add(parameter);
    }
    
    public Collection<Parameter> GetParameters()
    {
        return parameters;
    }
    
    public Collection<Parameter> GetSortedParameters()
    {
        return SortedParamters(parameters);
    }
    
    private Collection<Parameter> SortedParamters(Set<Parameter> parameters)
    {
        List<Parameter> result = new ArrayList<>(parameters);
        
        result.sort((a,b) ->a.GetName().compareTo(b.GetName()));
        
        return result;
        
    }


}
