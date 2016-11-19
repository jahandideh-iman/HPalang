/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Core.Statements;

import HPalang.Core.Statement;


/**
 *
 * @author Iman Jahandideh
 */
public class DelayStatement extends AbstractStatement<DelayStatement>
{
    private final float delay;
    
    public DelayStatement(float delay)
    {
        this.delay = delay;
    }
    
    public float GetDelay()
    {
        return delay;
    }

    @Override
    protected boolean InternalEquals(DelayStatement other)
    {
        return this.delay == other.delay;
    }

    @Override
    protected int InternalHashCode()
    {
        int hash = 3;
        hash = 97 * hash + Float.floatToIntBits(this.delay);
        return hash;
    }
       
    @Override
    public String toString()
    {
        return "delay(" + delay +")";
    }
}
