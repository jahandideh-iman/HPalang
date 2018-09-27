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
    public final boolean equals(Object other)
    {
        if(other == null)
            return false;
        if (!this.getClass().isAssignableFrom(other.getClass()))
            return false;
            
        //return InternalEquals((T) other);
        
        boolean temp = InternalEquals((T) other);
        if(temp == false)
        {
            temp = false;
            InternalEquals((T) other);
        }
        
        if(other == this && temp == false)
            throw new RuntimeException("Same But Not equal?!!");
        return temp;
    }
    
    @Override
    public final int hashCode()
    {
//        if(InternalHashCode() == 0 || InternalHashCode() == 1)
//            System.out.println(this.getClass().getName());
        return InternalHashCode();
        //return 0;
    }

    abstract protected boolean InternalEquals(T other);
    abstract protected int InternalHashCode();
}
