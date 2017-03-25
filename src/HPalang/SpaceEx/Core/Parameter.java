/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.SpaceEx.Core;

/**
 *
 * @author Iman Jahandideh
 */
public abstract class Parameter implements Visitable
{
    private String name;
    private boolean isLocal;
    
    public Parameter(String name, boolean isLocal)
    {
        this.name = name;
        this.isLocal = isLocal;
    }
    
    public String GetName()
    {
        return name;
    }
    
    public Boolean IsLocal()
    {
        return isLocal;
    }
}
