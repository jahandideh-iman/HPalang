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
public class ResumeStatement extends AbstractStatement<ResumeStatement>
{
    @Override
    protected boolean InternalEquals(ResumeStatement other)
    {
        return true;
    }

    @Override
    protected int InternalHashCode()
    {
        int hash = 3;
        return hash;
    }
    
    @Override
    public String toString()
    {
        return "resume";
    }
}
