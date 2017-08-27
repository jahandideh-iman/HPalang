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
public abstract class Equalitable<T>
{
    @Override
    public boolean equals(Object other)
    {
        if(other == null)
            return false;
        if (!this.getClass().isAssignableFrom(other.getClass()))
            return false;
            
        return InternalEquals((T) other);
        
//        boolean temp = InternalEquals((T) other);
//        if(temp == false)
//            temp = false;
//        return temp;
    }
    
    @Override
    public int hashCode()
    {
        return InternalHashCode();
    }

    abstract protected boolean InternalEquals(T other);
    abstract protected int InternalHashCode();
}
