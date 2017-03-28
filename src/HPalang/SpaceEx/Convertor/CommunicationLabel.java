/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.SpaceEx.Convertor;

import HPalang.Core.Equalitable;

/**
 *
 * @author Iman Jahandideh
 */
public class CommunicationLabel extends Equalitable<CommunicationLabel>
{
    private String label;
    private String handler;
    private boolean isSelf;
    
    
    public CommunicationLabel(String label, String handler, boolean isSelf)
    {
        this.label = label;
        this.handler = handler;
        this.isSelf = isSelf;
    }

    public String GetLabel()
    {
        return label;
    }

    public String GetHandler()
    {
        return handler;
    }

    public boolean IsSelf()
    {
        return isSelf;
    }

    @Override
    protected boolean InternalEquals(CommunicationLabel other)
    {
        return other.isSelf == this.isSelf 
                && other.handler.equals(handler)
                && other.label.equals(label);
    }

    @Override
    protected int InternalHashCode()
    {
        return 0;
    }
    
    
}