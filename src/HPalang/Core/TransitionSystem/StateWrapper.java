/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Core.TransitionSystem;

import HPalang.Core.Equalitable;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author Iman Jahandideh
 */
public interface StateWrapper<T> 
{
    public Collection<Transition<T>> OutTransitions();
    public Collection<Transition<T>> InTransitions();
    public void AddInTransition(Transition<T> tr);
    public void AddOutTransition(Transition<T> tr);
    public void RemoveInTransition(Transition<T> tr);
    public void RemoveOutTransition(Transition<T> tr);
    
    public T InnerState();
}
