/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Core;

import HPalang.Core.Variables.FloatVariable;
import HPalang.Core.Variables.IntegerVariable;
import HPalang.Core.Variables.RealVariable;

/**
 *
 * @author Iman Jahandideh
 */
public interface VariableVisitor
{
    public void Visit(IntegerVariable var);
    public void Visit(FloatVariable var);
    public void Visit(RealVariable var);
    
    // NOTE: This is only for Mocked Variables.
    public void Visit(Variable var);
}
