/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Core.DiscreteExpressions.BinaryOperators;

import HPalang.Core.DiscreteExpressions.BinaryOperator;
import HPalang.Core.Equalitable;

/**
 *
 * @author Iman Jahandideh
 */
public abstract class BinaryOperatorT<T> extends  Equalitable<T> implements  BinaryOperator
{

    @Override
    protected boolean InternalEquals(T other)
    {
        return true;
    }

    @Override
    protected int InternalHashCode()
    {
        return this.getClass().hashCode();
    }
    
}
