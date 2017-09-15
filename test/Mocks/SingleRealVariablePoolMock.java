/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Mocks;

import HPalang.Core.ContinuousVariable;
import HPalang.Core.Equalitable;
import HPalang.Core.RealVariablePool;
import HPalang.Core.Variables.RealVariable;

/**
 *
 * @author Iman Jahandideh
 */
public class SingleRealVariablePoolMock extends Equalitable<SingleRealVariablePoolMock> implements RealVariablePool
{
    private final RealVariable var;
    
    private boolean isAcquired = false;
    public SingleRealVariablePoolMock(RealVariable var)
    {
        this.var = var;
    }
    
    @Override
    public RealVariable Acquire()
    {
        isAcquired = true;
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

    @Override
    public boolean HasAnyAvailableVariable()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean HasAvailableVariable(int number)
    {
        return number == 1 && isAcquired == false;
    }

    @Override
    protected boolean InternalEquals(SingleRealVariablePoolMock other)
    {
        return this.isAcquired == other.isAcquired &&
                this.var.equals(other.var);
    }

    @Override
    protected int InternalHashCode()
    {
        return 0;
    }
    
}
