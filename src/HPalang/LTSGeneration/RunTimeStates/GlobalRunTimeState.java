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
import java.util.ArrayList;
import java.util.Collection;

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
    
    public Collection<ActorState> ActorStates()
    {
        Collection<SoftwareActorState> softwareStates = DiscreteState().ActorStates();
        Collection<PhysicalActorState> physicalStates = ContinuousState().ActorStates();
        
        Collection<ActorState> actorStates = new ArrayList<>(softwareStates.size()+physicalStates.size());
        
        actorStates.addAll(softwareStates);      
        actorStates.addAll(physicalStates);
        
        return actorStates;
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
