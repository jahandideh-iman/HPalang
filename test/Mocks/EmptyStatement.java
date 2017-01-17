/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Mocks;

import HPalang.Core.Statements.AbstractStatement;

/**
 *
 * @author Iman Jahandideh
 */
public class EmptyStatement extends AbstractStatement<EmptyStatement>
{
    private final String id;
    public EmptyStatement(String id)
    {
        this.id = id;
    }
    
    public EmptyStatement()
    {
        this.id = "";
    }
    @Override
    protected boolean InternalEquals(EmptyStatement other)
    {
        return id.equals(other.id);
    }

    @Override
    protected int InternalHashCode()
    {
        return 1;
    }
    
}
