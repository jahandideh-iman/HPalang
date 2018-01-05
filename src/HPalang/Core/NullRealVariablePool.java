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
public class NullRealVariablePool extends Equalitable<NullRealVariablePool> implements RealVariablePool
{
    @Override
    public RealVariable Acquire()
    {
        throw new ShouldNotBeUsedException();
    }

    @Override
    public void Release(RealVariable variable)
    {
       throw new ShouldNotBeUsedException();
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

    @Override
    public boolean HasAnyAvailableVariable()
    {
        throw new ShouldNotBeUsedException();
    }

    @Override
    public boolean HasAvailableVariable(int number)
    {
        return number == 0;
    }

    @Override
    public Iterable<RealVariable> AllVariables()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Iterable<RealVariable> AvailableVariables()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected boolean InternalEquals(NullRealVariablePool other)
    {
        return true;
    }

    @Override
    protected int InternalHashCode()
    {
        return 13;
    }

    
}
