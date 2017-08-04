/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Iman Jahandideh
 */
public interface CompositeState extends State
{
    public CompositeState AddSubstate(State substate);
    public <T extends State> T FindSubState(Class<T> clazz);
    public <T extends State> Collection<T> FindSubStates(Class<T> clazz);
    public Collection<State> Substates();
}
