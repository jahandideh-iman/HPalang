/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.RunTimeStates.Event;

import HPalang.Core.Equalitable;
import HPalang.Core.SoftwareActor;
import HPalang.LTSGeneration.RunTimeStates.GlobalRunTimeState;

/**
 *
 * @author Iman Jahandideh
 */
public class ResumeSoftwareActorAction extends Equalitable<ResumeSoftwareActorAction> implements Action 
{

    private final SoftwareActor actor;
    public ResumeSoftwareActorAction(SoftwareActor actor)
    {
        this.actor = actor;
    }
    @Override
    protected boolean InternalEquals(ResumeSoftwareActorAction other)
    {
        return this.actor.equals(other.actor);
    }

    @Override
    protected int InternalHashCode()
    {
        return 0;
    }

    @Override
    public void Execute(GlobalRunTimeState globalState)
    {
        globalState.DiscreteState().FindActorState(actor).SetSuspended(false);
    }
    
}
