/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration;

import HPalang.LTSGeneration.Labels.Reset;
import HPalang.Core.Equalitable;
import HPalang.LTSGeneration.Labels.Guard;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 *
 * @author Iman Jahandideh
 */
public abstract class Label<T extends Label> extends Equalitable<T> 
{
    protected final Set<Reset> resets = new LinkedHashSet<>();
    protected final Guard  guard;
    
    public Label()
    {
        guard = null;
    }
    
    public Label(Guard guard)
    {
        this.guard = guard;
    }
    
    public Label(Guard guard,Set<Reset> resets)
    {
        this.guard = guard;
        this.resets.addAll(resets);
    }
    
    public Label(Set<Reset> resets)
    {
        this(null, resets);
    }
    
    public Set<Reset> Resets()
    {
        return resets;
    }
    
    public boolean IsGuarded()
    {
        return guard != null;
    }
    
    public Guard Guard()
    {
        return guard;
    }
    
    @Override
    protected boolean InternalEquals(T other)
    {
        return this.resets.equals(other.Resets())
                && GuardEqual(other.guard);
    }
    
    
    private boolean GuardEqual(Guard otherGuard)
    {
        if(guard == null && otherGuard == null)
            return true;
        else if(guard == null || otherGuard == null) // NOTE: "||" works as xor in this context.
            return false;
                    
        return guard.equals(otherGuard);
    }
}
