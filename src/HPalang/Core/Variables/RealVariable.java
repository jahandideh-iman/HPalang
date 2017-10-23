/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Core.Variables;

import HPalang.Core.Variable;
import HPalang.Core.VariableVisitor;

/**
 *
 * @author Iman Jahandideh
 */
public final class RealVariable extends Variable
{
    
    public RealVariable(String name)
    {
        super(name, Type.real);
    }
    
    @Override
    public void Visit(VariableVisitor visitor)
    {
        visitor.Visit(this);
    }
}
