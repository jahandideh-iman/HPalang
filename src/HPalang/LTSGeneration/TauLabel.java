/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration;

import HPalang.Core.Equalitable;

/**
 *
 * @author Iman Jahandideh
 */
public class TauLabel extends Equalitable<TauLabel> implements Label
{
    private Reset reset = null;
    
    public TauLabel()
    {
        
    }
    
    public TauLabel(Reset reset)
    {
        this.reset = reset;    
    }
    
    @Override
    public String toString()
    {
        return "t";
    }

    @Override
    protected boolean InternalEquals(TauLabel other)
    {
        if(this.reset == null && other.reset == null)
            return true;
        else if(this.reset == null || other.reset == null)
            return false;
        else
            return other.reset.equals(this.reset);
    }

    @Override
    protected int InternalHashCode()
    {
        int hash = 7;
        return hash;
    }
}
