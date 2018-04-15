/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.RunTimeStates;

import HPalang.Core.Actor;
import HPalang.Core.PhysicalActor;
import HPalang.Core.SoftwareActor;
import HPalang.LTSGeneration.CompositeStateT;

/**
 *
 * @author Iman Jahandideh
 */
public class GlobalRunTimeState extends CompositeStateT<GlobalRunTimeState> 
{

    public ContinuousState ContinuousState()
    {
        return FindSubState(ContinuousState.class);
    }
    
    public DiscreteState DiscreteState()
    {
        return FindSubState(DiscreteState.class);
    }
    
    public EventsState EventsState()
    {
        return FindSubState(EventsState.class);
    }
    
    public NetworkState NetworkState()
    {
        return FindSubState(NetworkState.class);
    }
    
    public VariablePoolState VariablePoolState()
    {
        return FindSubState(VariablePoolState.class);
    }
    
    public ActorState FindActorState(Actor actor)
    {
        if(actor instanceof SoftwareActor)
            return DiscreteState().FindActorState((SoftwareActor)actor);
        else if (actor instanceof PhysicalActor)
            return ContinuousState().FindActorState((PhysicalActor)actor);
        
        return null;
    }
    
    @Override
    protected boolean DataEquals(GlobalRunTimeState other)
    {
        return true;
    }

    @Override
    protected GlobalRunTimeState NewInstance()
    {
        return new GlobalRunTimeState();
    }

    @Override
    protected void CloneData(GlobalRunTimeState copy)
    {
    }

}
