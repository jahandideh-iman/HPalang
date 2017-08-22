/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Core;

/**
 *
 * @author Iman Jahandideh
 */
public class Actor
{
    private final String name;
    
    public Actor(String name)
    {
        this.name = name;
    }
    
    public String Name()
    {
        return name;
    }
    
    public void BindInstance(InstanceParameter parameter, Actor instance)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void BindDelegation(DelegationParameter parameter, Delegation delegation)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
