/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Statements;

import HPalang.LTSGeneration.RunTimeStates.ContinuousBehavior;

/**
 *
 * @author Iman Jahandideh
 */
public class ContinuousBehaviorStatement implements Statement
{
    private final ContinuousBehavior behavior;

    public ContinuousBehaviorStatement(ContinuousBehavior continuousBehavior)
    {
        behavior = continuousBehavior;
    }

    public ContinuousBehavior GetBehavior()
    {
        return behavior;
    }
    
    @Override
    public boolean equals(Object other)
    {
        if(other == null)
            return false;
        
        if (!this.getClass().isAssignableFrom(other.getClass()))
            return false;
            
        ContinuousBehaviorStatement otherS = (ContinuousBehaviorStatement) other;
       
        return this.behavior.equals(otherS.behavior);
    }
    
    @Override
    public String toString()
    {
        return "(" + behavior.toString() +")";
    }
    
}
