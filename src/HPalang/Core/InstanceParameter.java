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
public class InstanceParameter
{
    private final String name;
    private final SoftwareActorType type;
    
    private SoftwareActor instance;
    
    public InstanceParameter(String name, SoftwareActorType type)
    {
        this.name = name;
        this.type = type;
    }
    
    public void Bind(SoftwareActor instance)
    {
        assert (this.instance == null);
        assert (this.type.equals(instance.Type()));
        
        this.instance = instance;
    }
}
