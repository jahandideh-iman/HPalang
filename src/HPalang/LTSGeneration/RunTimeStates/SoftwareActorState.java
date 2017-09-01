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
public class SoftwareActorState extends ActorState<SoftwareActorState>
{    
    private boolean isSuspended = false;
   
    public SoftwareActorState(SoftwareActor actor)
    {
        super(actor);
    }
    
    public MessageQueueState MessageQueueState()
    {
        return FindSubState(MessageQueueState.class);
    }
    
    public ValuationState ValuationState()
    {
        return FindSubState(ValuationState.class);
    }
    
    public int GetMessageQueueCapacity()
    {
        return SActor().Capacity();
    }
    
    public SoftwareActor SActor()
    {
       return (SoftwareActor)Actor();
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
        return new SoftwareActorState(SActor());
    }

    @Override
    protected void CloneData(SoftwareActorState copy)
    {
        copy.isSuspended = isSuspended;
    }

    @Override
    protected boolean DataEquals(SoftwareActorState other)
    {
        return super.DataEquals(other) && 
                isSuspended == other.isSuspended;
    }

}
