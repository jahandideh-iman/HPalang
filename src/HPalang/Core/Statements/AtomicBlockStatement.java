/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Core.Statements;

import HPalang.Core.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author Iman Jahandideh
 */
public class AtomicBlockStatement extends AbstractStatement<AtomicBlockStatement>
{
    private final List<Statement> statements; 
    
    AtomicBlockStatement(List<Statement> statements)
    {
        this.statements = new ArrayList<>(statements);
    }

    @Override
    protected boolean InternalEquals(AtomicBlockStatement other)
    {
        return this.statements.equals(other.statements);
    }

    @Override
    protected int InternalHashCode()
    {
        return statements.hashCode();
    }

}
