/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.RunTimeStates;

import HPalang.Core.SoftwareActor;
import HPalang.LTSGeneration.CompositeStateT;

/**
 *
 * @author Iman Jahandideh
 */
public class SoftwareActorState extends CompositeStateT<SoftwareActorState>
{
    private final SoftwareActor actor;
    
    private boolean isSuspended = false;
   
    public SoftwareActorState(SoftwareActor actor)
    {
        this.actor = actor;     
    }
    
    public ExecutionQueueState ExecutionQueueState()
    {
        return FindSubState(ExecutionQueueState.class);
    }

    public MessageQueueState MessageQueueState()
    {
        return FindSubState(MessageQueueState.class);
    }
    
    public int GetMessageQueueCapacity()
    {
        return actor.GetCapacity();
    }
    
    public SoftwareActor Actor()
    {
       return actor;
    }
       
    public void SetSuspended(boolean suspended)
    {
        isSuspended = suspended;
    }
    
    public boolean IsSuspended()
    {
        return isSuspended;
    }
    
    @Override
    protected SoftwareActorState NewInstance()
    {
        return new SoftwareActorState(actor);
    }

    @Override
    protected void CloneData(SoftwareActorState copy)
    {
        copy.isSuspended = isSuspended;
    }

    @Override
    protected boolean DataEquals(SoftwareActorState other)
    {
        return this.actor == other.actor && 
                isSuspended == other.isSuspended;
    }

}
