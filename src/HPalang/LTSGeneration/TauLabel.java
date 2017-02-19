/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration;

import HPalang.Core.Equalitable;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Iman Jahandideh
 */
public class TauLabel extends Label<TauLabel>
{  
    public TauLabel()
    {
        
    }
    
    public TauLabel(Set<Reset> resets) 
    {
        super(resets);
    }
    
    @Override
    public String toString()
    {
        return "t";
    }

    @Override
    protected boolean InternalEquals(TauLabel other)
    {
        return other.GetResets().equals(this.resets);
    }

    @Override
    protected int InternalHashCode()
    {
        int hash = 7;
        return hash;
    }
}
