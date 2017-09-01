/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HPalang.LTSGeneration.RunTimeStates;

import HPalang.Core.PhysicalActor;
import HPalang.Core.SoftwareActor;
import HPalang.LTSGeneration.CompositeStateT;
import HPalang.LTSGeneration.State;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Iman Jahandideh
 */
public class DiscreteState extends CompositeStateT<DiscreteState>
{
    private final List<SoftwareActorState> softwareActorStatesCache = new LinkedList<>();
    
    public void AddSoftwareActorState(SoftwareActorState actorState)
    {
       AddSubstate(actorState);
    }  

    @Override
    protected void OnSubstateAdded(State substate)
    {
        if(substate instanceof SoftwareActorState)
            softwareActorStatesCache.add((SoftwareActorState)substate);
    }
        
    
    public Iterable<SoftwareActorState> ActorStates()
    {
        return softwareActorStatesCache;
    }
        
    @Override
    protected DiscreteState NewInstance()
    {
        return new DiscreteState();
    }

    @Override
    protected void CloneData(DiscreteState copy)
    {
    }

    @Override
    protected boolean DataEquals(DiscreteState other)
    {
        return true;
    }
    
    public SoftwareActorState FindActorState(SoftwareActor actor)
    {
        for(SoftwareActorState state : softwareActorStatesCache)
            if(state.SActor() == actor)
                return state;
        return null;
    }    
}