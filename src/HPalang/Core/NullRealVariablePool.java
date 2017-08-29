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
public class NullRealVariablePool implements RealVariablePool
{

    @Override
    public RealVariable Acquire()
    {
        return null;
    }

    @Override
    public void Release(RealVariable variable)
    {
       
    }

    @Override
    public boolean Has(RealVariable variable)
    {
        return false;
    }

    @Override
    public RealVariablePool DeepCopy()
    {
        return this;
    }
    
}
