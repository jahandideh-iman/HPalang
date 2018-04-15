/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.Labels;

import HPalang.Core.TransitionSystem.Label;
import HPalang.LTSGeneration.Labels.Reset;
import java.util.Set;


/**
 *
 * @author Iman Jahandideh
 */
@Deprecated
public class GuardedlLabel extends Label<GuardedlLabel>
{
    private final String guard;
    
    public GuardedlLabel(String guard)
    {
        this.guard = guard;
    }
    
    public GuardedlLabel(String guard, Set<Reset> resets)
    {
        super(resets);
        this.guard = guard;
    }
    
    public String GetGuard()
    {
        return guard;
    }
      
    @Override
    public String toString()
    {
        return guard;
    }

    @Override
    protected boolean InternalEquals(GuardedlLabel other)
    {
        return this.guard.equals(other.guard)
                && other.Resets().equals(this.resets);
    }

}
