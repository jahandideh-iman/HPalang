/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Mocks;

import HPalang.Core.Variable;
import HPalang.Core.VariableVisitor;

/**
 *
 * @author Iman Jahandideh
 */
public class FakeVariable extends Variable
{
    
    public FakeVariable(String name)
    {
        super(name);
    }
    
    public FakeVariable(String name, Variable.Type type)
    {
        super(name, type);
    }

    @Override
    public void Visit(VariableVisitor visitor)
    {
        visitor.Visit((Variable)this);
    }
    
}
