/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Core.Statements;

import HPalang.Core.Statement;
import HPalang.LTSGeneration.RunTimeStates.ContinuousBehavior;

/**
 *
 * @author Iman Jahandideh
 */
public class ContinuousBehaviorStatement extends AbstractStatement<ContinuousBehaviorStatement>
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
    public String toString()
    {
        return "(" + behavior.toString() +")";
    }

    @Override
    protected boolean InternalEquals(ContinuousBehaviorStatement other)
    {
        return this.behavior.equals(other.behavior);
    }

    @Override
    protected int InternalHashCode()
    {
        return behavior.hashCode();
    }
    
}
