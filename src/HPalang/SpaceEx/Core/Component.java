/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.SpaceEx.Core;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Iman Jahandideh
 */
public abstract class Component implements Visitable
{
    List<Parameter> parameters = new LinkedList<>();
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
    
    public List<Parameter> GetParameters()
    {
        return parameters;
    }
}
