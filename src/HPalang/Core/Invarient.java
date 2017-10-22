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
public class Invarient extends Equalitable<Invarient>
{
    private String invarient;
    
    public Invarient(String invarient)
    {
        this.invarient = invarient;
    }
    
    @Override
    public String toString()
    {
        return invarient;
    }

    @Override
    protected boolean InternalEquals(Invarient other)
    {
        return this.invarient.equals(other.invarient);
    }

    @Override
    protected int InternalHashCode()
    {
        return 0;
    }
}
