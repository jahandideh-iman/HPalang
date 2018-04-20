/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.Core.Statements;

import HPalang.Core.ActorLocator;
import HPalang.Core.Mode;

/**
 *
 * @author Iman Jahandideh
 */
public class ModeChangeStatement extends AbstractStatement<ModeChangeStatement>
{
    private final Mode mode;
    
    public ModeChangeStatement(Mode mode)
    {
        this.mode = mode;
    }
    
    public Mode Mode()
    {
        return mode;
    }
    
    @Override
    protected boolean InternalEquals(ModeChangeStatement other)
    {
        return mode.equals(other.mode);
    }

    @Override
    protected int InternalHashCode()
    {
        return mode.hashCode();
    }   
}
