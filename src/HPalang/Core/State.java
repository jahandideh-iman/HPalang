/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Core;

import java.util.Collection;

/**
 *
 * @author Iman Jahandideh
 */
public interface State extends DeepClonable<State>
{    
    public void AddSubstate(State substate);
    
    public <T extends State> T FindSubState(Class<T> clazz);
    public <T extends State> Collection<T> FindSubStates(Class<T> clazz);
    
    public Collection<State> Substates();
}
