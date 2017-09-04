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
public class RequestModeChangeStatement extends AbstractStatement<RequestModeChangeStatement>
{
    private final ActorLocator actorLocator;
    private final Mode mode;
    
    public RequestModeChangeStatement(ActorLocator actorLocator, Mode mode)
    {
        this.actorLocator = actorLocator;
        this.mode = mode;
    }
    
    @Override
    protected boolean InternalEquals(RequestModeChangeStatement other)
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
