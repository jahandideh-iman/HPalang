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
public abstract class DiscreteExpression<T> extends Equalitable<T>
{
    public abstract int Evaluate(ValuationContainer valuations);
}
