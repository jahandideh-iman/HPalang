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
public interface SOSRule
{
    public void TryApply(StateInfo globalStateInfo, TransitionCollector collector); 
}
