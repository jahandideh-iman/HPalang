/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration;

import HPalang.LTSGeneration.RunTimeStates.GlobalRunTimeState;
import java.util.Collection;

/**
 *
 * @author Iman Jahandideh
 */
public interface SOSRule
{
    public void TryApply(StateInfo globalStateInfo, LTSGenerator generator); 
}
