/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Utilities;

import java.util.ArrayDeque;

/**
 *
 * @author Iman Jahandideh
 */
public class EqualitableArrayDeque<T> extends ArrayDeque<T>
{

    @Override
    public boolean equals(Object other)
    {
        if(other == null)
            return false;
        if (!this.getClass().isAssignableFrom(other.getClass()))
            return false;
            
        EqualitableArrayDeque<T> otherDeque = (EqualitableArrayDeque<T>)(other);
        
        if(otherDeque.size() == this.size())
        {
            Object[] otherArray = otherDeque.toArray();
            Object[] thisArray = this.toArray();
            for (int i = 0; i < otherArray.length; i++)
            {
                if(otherArray[i].equals(thisArray[i])==false)
                    return false;
            }
            return true;
        }
        else
        {
            return false;
        }
    }

    
    @Override
    public int hashCode()
    {
        return 1;
    }
    
}
