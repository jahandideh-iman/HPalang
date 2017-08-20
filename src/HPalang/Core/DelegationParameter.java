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
public class DelegationParameter extends Equalitable<DelegationParameter>
{
    private final String name;
    private final InstanceParameter instanceParameter;
    private final MessageHandler messageHandler;
    
    public DelegationParameter(String name)
    {
        this.name = name;
        this.instanceParameter = null;
        this.messageHandler = null;
    }
    
    
    public InstanceParameter InstanceParameter()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public MessageHandler MessageHandler()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public String Name()
    {
        return name;
    }
    
    @Override
    protected boolean InternalEquals(DelegationParameter other)
    {
        return name.equals(other.name);
    }

    @Override
    protected int InternalHashCode()
    {
        return 0;
    }

}
