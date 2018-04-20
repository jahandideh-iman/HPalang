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
public class InstanceParameter extends Equalitable<InstanceParameter>
{
    private final String name;
    private final ActorType type;
    
    
    public InstanceParameter(String name, ActorType type)
    {
        this.name = name;
        this.type = type;
    }
    
//    public void Bind(SoftwareActor instance)
//    {
//        assert (this.instance == null);
//        assert (this.type.equals(instance.Type()));
//        
//        this.instance = instance;
//    }

    @Override
    protected boolean InternalEquals(InstanceParameter other)
    {
        return name.equals(other.name) &&
                type.equals(other.type);
    }

    @Override
    protected int InternalHashCode()
    {
        return name.hashCode();
    }

    public String Name()
    {
        return name;
    }
    
    public ActorType Type()
    {
        return type;
    }
}
