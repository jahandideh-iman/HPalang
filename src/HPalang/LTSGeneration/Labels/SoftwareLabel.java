/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.Labels;

import HPalang.LTSGeneration.Label;
import java.util.Set;

/**
 *
 * @author Iman Jahandideh
 */
public class SoftwareLabel extends Label<SoftwareLabel>
{
    public SoftwareLabel()
    {
        super();
    }
        
    public SoftwareLabel(Guard guard) 
    {
        super(guard);
    }
    
    public SoftwareLabel(Set<Reset> resets) 
    {
        super(resets);
    }
    
    public SoftwareLabel(Guard guard, Set<Reset> resets)
    {
        super(guard, resets);
    }


    
    @Override
    public String toString()
    {
        return "s";
    }

    @Override
    protected boolean InternalEquals(SoftwareLabel other)
    {
        return super.InternalEquals(other);
    }

}
