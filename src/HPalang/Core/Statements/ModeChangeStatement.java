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

    private final ActorLocator actorLocator;
    private final Mode mode;
    
    public ModeChangeStatement(ActorLocator actorLocator, Mode mode)
    {
        this.actorLocator = actorLocator;
        this.mode = mode;
    }
    
    @Override
    protected boolean InternalEquals(ModeChangeStatement other)
    {
        return actorLocator.equals(other.actorLocator) &&
                mode.equals(other.mode);
    }

    @Override
    protected int InternalHashCode()
    {
        return 0;
    }
    
}
