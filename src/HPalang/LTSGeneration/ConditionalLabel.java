/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration;


/**
 *
 * @author Iman Jahandideh
 */
public class ConditionalLabel implements Label
{
    private String condition;
    
    public ConditionalLabel(String condition)
    {
        this.condition = condition;
    }
    
    public String GetCondition()
    {
        return condition;
    }
    
    @Override
    public boolean equals(Object other)
    {
         if(other == null)
            return false;
        
        if (!this.getClass().isAssignableFrom(other.getClass()))
            return false;
            
        ConditionalLabel otherL = (ConditionalLabel) other;
        
        return this.condition.equals(otherL.condition);
    }
    
    @Override
    public String toString()
    {
        return condition;
    }
}
