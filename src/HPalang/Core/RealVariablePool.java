/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Core;

import HPalang.Core.Variables.RealVariable;

/**
 *
 * @author Iman Jahandideh
 */
public interface RealVariablePool extends DeepClonable<RealVariablePool>
{
    public RealVariable Acquire();
    public void Release(RealVariable variable);
    
    public boolean Has(RealVariable variable);
}
