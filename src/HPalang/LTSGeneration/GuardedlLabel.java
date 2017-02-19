/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration;


/**
 *
 * @author Iman Jahandideh
 */
public class GuardedlLabel implements Label
{
    private String guard;
    
    public GuardedlLabel(String guard)
    {
        this.guard = guard;
    }
    
    public String GetGuard()
    {
        return guard;
    }
    
    @Override
    public boolean equals(Object other)
    {
         if(other == null)
            return false;
        
        if (!this.getClass().isAssignableFrom(other.getClass()))
            return false;
            
        GuardedlLabel otherL = (GuardedlLabel) other;
        
        return this.guard.equals(otherL.guard);
    }
    
    @Override
    public String toString()
    {
        return guard;
    }
}
