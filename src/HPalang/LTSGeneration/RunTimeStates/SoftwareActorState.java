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

   
    public SoftwareActorState(SoftwareActor actor)
    {
        super(actor);
    }
    
    public SoftwareActor SActor()
    {
       return (SoftwareActor)Actor();
    }
    
    @Override
    public void SetSuspended(boolean suspended)
    {
        isSuspended = suspended;
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

//    @Override
//    protected boolean DataEquals(SoftwareActorState other)
//    {
//        return super.DataEquals(other) && 
//                isSuspended == other.isSuspended;
//    }
//
//    @Override
//    protected int DataHashCode()
//    {
//        return super.DataHashCode() + Boolean.hashCode(isSuspended);
//    }
//    

}
