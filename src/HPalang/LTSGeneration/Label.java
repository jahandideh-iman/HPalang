/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration;

import HPalang.LTSGeneration.Labels.Reset;
import HPalang.Core.Equalitable;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Iman Jahandideh
 */
public abstract class Label<T extends Label> extends Equalitable<T> 
{
    protected Set<Reset> resets = new LinkedHashSet<>();
    
    public Label()
    {
    }
    
    public Label(Set<Reset> resets)
    {
        this.resets.addAll(resets);
    }
    
    public Set<Reset> GetResets()
    {
        return resets;
    }
}
