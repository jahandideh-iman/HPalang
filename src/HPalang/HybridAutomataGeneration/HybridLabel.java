/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.HybridAutomataGeneration;

import HPalang.LTSGeneration.Label;
import HPalang.LTSGeneration.Labels.Guard;
import HPalang.LTSGeneration.Labels.Reset;
import java.util.Set;

/**
 *
 * @author Iman Jahandideh
 */
public class HybridLabel extends Label<HybridLabel>
{

    private final boolean isASAP;
    
    public HybridLabel(Guard guard, boolean isASAP)
    {
        super(guard);
        this.isASAP = isASAP;
    }
    
    public HybridLabel(Guard guard,Set<Reset> resets)
    {
        this(guard, resets,false);
    }
    
    public HybridLabel(Guard guard, Set<Reset> resets, boolean isASAP)
    {
        super(guard, resets);
        this.isASAP = isASAP;
    }
    
    @Override
    protected int InternalHashCode()
    {
       return super.hashCode();
    }
    
}
