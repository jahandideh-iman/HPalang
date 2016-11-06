/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Core;

/**
 *
 * @author Iman Jahandideh
 * @param <T>
 */
public interface DeepClonable<T> extends Cloneable
{
    public T DeepCopy();
}
