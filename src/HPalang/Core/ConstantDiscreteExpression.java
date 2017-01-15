/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Core;

/**
 *
 * @author Iman Jahandideh
 */
public class ConstantDiscreteExpression implements DiscreteExpression
{
    private final int constant;
    
    public ConstantDiscreteExpression(int constant)
    {
        this.constant = constant;
    }
    
    @Override
    public int Evaluate()
    {
        return constant;
    }
    
}
