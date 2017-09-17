/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.Labels;

import HPalang.LTSGeneration.Label;
import java.util.Collections;
import java.util.Set;

/**
 *
 * @author Iman Jahandideh
 */
public class ContinuousLabel extends Label<ContinuousLabel>
{
    public ContinuousLabel(Guard guard)
    {
        this(guard, Collections.EMPTY_SET);
    }
        
    public ContinuousLabel(Guard guard, Set<Reset> resets)
    {
        super(guard, resets);
    }
    
    @Override
    protected boolean InternalEquals(ContinuousLabel other)
    {
        return super.InternalEquals(other) && guard.equals(other.guard);
    }

    @Override
    protected int InternalHashCode()
    {
        return guard.hashCode();
    }
    
}
