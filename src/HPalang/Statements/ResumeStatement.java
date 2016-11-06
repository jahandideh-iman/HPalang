/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Statements;

/**
 *
 * @author Iman Jahandideh
 */
public class ResumeStatement implements Statement
{
    @Override
    public boolean equals(Object other)
    {
        if(other == null)
            return false;
        
        return this.getClass().isAssignableFrom(other.getClass());
    }
    
    @Override
    public String toString()
    {
        return "resume";
    }
}
