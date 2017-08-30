/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Mocks;

import HPalang.Core.ContinuousVariable;
import HPalang.Core.RealVariablePool;
import HPalang.Core.Variables.RealVariable;

/**
 *
 * @author Iman Jahandideh
 */
public class SingleRealVariablePoolMock implements RealVariablePool
{
    private final RealVariable var;
    public SingleRealVariablePoolMock(RealVariable var)
    {
        this.var = var;
    }
    
    @Override
    public RealVariable Acquire()
    {
        return var;
    }

    @Override
    public void Release(RealVariable variable)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean Has(RealVariable variable)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public RealVariablePool DeepCopy()
    {
        return new SingleRealVariablePoolMock(var);
    }
    
}