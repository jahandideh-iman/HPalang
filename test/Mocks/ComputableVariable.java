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
public class ComputableVariable extends Variable
{
    
    public ComputableVariable(String name)
    {
        super(name);
    }

    @Override
    public void Visit(VariableVisitor visitor)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
